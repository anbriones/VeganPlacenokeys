package com.example.veganplace;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.veganplace.data.lecturaapinoticias.NoticiasNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.CoordenadasNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.FotosresNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.RestaurantesNetWorkDatasource;
import com.example.veganplace.data.lecturarectas.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturarectas.RecetasNetworkDataSource;
import com.example.veganplace.data.modelmapas.Location;
import com.example.veganplace.data.modelmapas.Photo;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelnoticias.Article;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.data.modelusuario.User;
import com.example.veganplace.data.roomdatabase.DaoFotosRestaurant;
import com.example.veganplace.data.roomdatabase.DaoIngrediente;
import com.example.veganplace.data.roomdatabase.DaoLocation;
import com.example.veganplace.data.roomdatabase.DaoNoticia;
import com.example.veganplace.data.roomdatabase.DaoReceta;
import com.example.veganplace.data.roomdatabase.DaoResenia;
import com.example.veganplace.data.roomdatabase.DaoResult;
import com.example.veganplace.data.roomdatabase.DaoUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class VeganPlaceRepository {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();


    // For Singleton instantiation
    private static VeganPlaceRepository sInstance;
    private final DaoReceta mrecetasdao;
    private final DaoIngrediente mingredientesdao;
    private final DaoNoticia mnoticiasDao;
    private final DaoUsuario musuariosdao;
    private final DaoResult mrestaurantes;
    private final DaoLocation mlocation;
    private final DaoFotosRestaurant mfotosres;
    private final DaoResenia mresenia;


    private final RecetasNetworkDataSource mRecetaNetworkDataSource;
    private final IngredienteNetworkDataSource mIngretaNetworkDataSource;
    private final NoticiasNetWorkDatasource mNoticiasNetworkDataSource;
    private final RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource;
    private final CoordenadasNetWorkDatasource mLocationNetworkdataSource;
    private final FotosresNetWorkDatasource mFotosresNetWorkDatasource;
    private final FirebaseFirestore firebase;

    private final AppExecutors mExecutors = AppExecutors.getInstance();

    private final MutableLiveData<String> tipoFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> filtronombre = new MutableLiveData<>();
    private final MutableLiveData<String> filtronombre2 = new MutableLiveData<>();
    private final MutableLiveData<String> filtropassword = new MutableLiveData<>();
    private final MutableLiveData<String> filtronamerestaurante = new MutableLiveData<>();
    private final MutableLiveData<String> filtroaddressres = new MutableLiveData<>();
    private final MutableLiveData<String> filtrobusquda = new MutableLiveData<>();

    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();

    private VeganPlaceRepository(DaoReceta mrecetasdao, DaoIngrediente mingredientesdao, DaoNoticia mnoticiasDao, DaoUsuario muserdao, DaoResult mrestaurantes, DaoLocation mlocation, DaoFotosRestaurant mfotos, DaoResenia mresenia, FirebaseFirestore firebase,IngredienteNetworkDataSource mIngretaNetworkDataSource, RecetasNetworkDataSource mRecetaNetworkDataSource1, NoticiasNetWorkDatasource mNoticiasNetworkDataSource, RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource, CoordenadasNetWorkDatasource mCoordenadasNetworkDtaSource, FotosresNetWorkDatasource mFotosresNetWorkDatasource) {
        this.mrecetasdao = mrecetasdao;
        this.mingredientesdao = mingredientesdao;
        this.mnoticiasDao = mnoticiasDao;
        this.musuariosdao = muserdao;
        this.mrestaurantes = mrestaurantes;
        this.mlocation = mlocation;
        this.mfotosres = mfotos;
        this.mresenia = mresenia;
        this.mRecetaNetworkDataSource = mRecetaNetworkDataSource1;
        this.mIngretaNetworkDataSource = mIngretaNetworkDataSource;
        this.mNoticiasNetworkDataSource = mNoticiasNetworkDataSource;
        this.mRestaurantesNetwoekdataSource = mRestaurantesNetwoekdataSource;
        this.mLocationNetworkdataSource = mCoordenadasNetworkDtaSource;
        this.mFotosresNetWorkDatasource = mFotosresNetWorkDatasource;
        this.firebase = firebase;


        dofectchdatos();
        //
        // LiveData that fetches alimentosjson from network
        LiveData<Recipe[]> networkData = this.mRecetaNetworkDataSource.getCurrentrecetas();
        LiveData<Ingredient[]> networkDataing = this.mIngretaNetworkDataSource.getcurrentingredientes();
        LiveData<Article[]> networkNoticias = this.mNoticiasNetworkDataSource.getcurrentnoticias();
        LiveData<Result[]> networkRestaurantes = this.mRestaurantesNetwoekdataSource.getcurrentrestaurantes();
        LiveData<Location[]> networkLocation = this.mLocationNetworkdataSource.getcurrentlocalizaciones();
        LiveData<Photo[]> networkPhoto = this.mFotosresNetWorkDatasource.getcurrentfotos();


        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newRecetasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mrecetasdao.insertarReceta(Arrays.asList(newRecetasFromNetwork));
                Log.d(LOG_TAG, "New values recipes inserted in Room");
            });
        });
        //Network de ingredientes
        networkDataing.observeForever(newIngreFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mingredientesdao.insertarIngrediente(Arrays.asList(newIngreFromNetwork));
                Log.d(LOG_TAG, "New values ingredients inserted in Room");
            });
        });

        //Network de noticias
        networkNoticias.observeForever(newNoticiasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mnoticiasDao.insertarNoticia(Arrays.asList(newNoticiasFromNetwork));
                Log.d(LOG_TAG, "New values news inserted in Room");
            });
        });

        //Network de restaurantes
        networkRestaurantes.observeForever(newRestauranteFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mrestaurantes.insertarRestaurante(Arrays.asList(newRestauranteFromNetwork));
                Log.d(LOG_TAG, "New values results  inserted in Room");
            });
        });

        //Network de localizaciones
        networkLocation.observeForever(newLocalizacionFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mlocation.insertarLocation(Arrays.asList(newLocalizacionFromNetwork));
                Log.d(LOG_TAG, "New values locations  inserted in Room");
            });
        });

        //Network de fotos
        networkPhoto.observeForever(newPhotoFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mfotos.insertarfoto(Arrays.asList(newPhotoFromNetwork));
                Log.d(LOG_TAG, "New values photos  inserted in Room");
            });
        });

    }

    public synchronized static VeganPlaceRepository getInstance(RecetasNetworkDataSource nds, IngredienteNetworkDataSource ndsi, NoticiasNetWorkDatasource ndn, RestaurantesNetWorkDatasource ndr, CoordenadasNetWorkDatasource ndl, FotosresNetWorkDatasource ndf, DaoReceta daoReceta, DaoIngrediente daoIngrediente, DaoNoticia daoNoticia, DaoUsuario daoUsuario, DaoResult daoresult, DaoLocation daoLocation, DaoFotosRestaurant daoFotos, DaoResenia daoresenia, FirebaseFirestore firebase) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new VeganPlaceRepository(daoReceta, daoIngrediente, daoNoticia, daoUsuario, daoresult, daoLocation, daoFotos, daoresenia,firebase, ndsi, nds, ndn, ndr, ndl, ndf);//Pasa las dependencias por constructor
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }


    public void dofectchdatos() {
        Log.d(LOG_TAG, "Fetching recetas from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mrecetasdao.eliminarrecetas();
            mRecetaNetworkDataSource.fetchRecetas();
        });
        Log.d(LOG_TAG, "Fetching ingredientes from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mingredientesdao.eliminaringredientes();
            mIngretaNetworkDataSource.fetchingredientes();
        });

        Log.d(LOG_TAG, "Fetching noticias from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mnoticiasDao.eliminarnoticias();
            mNoticiasNetworkDataSource.fetcnoticias();
        });
    }

    
    public void dofectchdatosmapa(String busqueda) {
        Log.d(LOG_TAG, "Fetching result(restaurantes)  from Json"+ busqueda.toString());
        AppExecutors.getInstance().diskIO().execute(() -> {
              mrestaurantes.eliminarrestaurantes();
            mRestaurantesNetwoekdataSource.fetchrestaurantes(busqueda);
        });

        Log.d(LOG_TAG, "Fetching localizaciones from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
              mlocation.eliminarLocalizaciones();
            mLocationNetworkdataSource.fetchlocation(busqueda);
        });

        Log.d(LOG_TAG, "Fetching fotos from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
             mfotosres.eliinarfotos();
            mFotosresNetWorkDatasource.fetchfotos(busqueda);
        });
    }

    //Devuelve Todos las recetas que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Recipe>> getrecetas() {
        return mrecetasdao.getrecetas();
    }

    //Devuelve Todos los ingredientes que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Ingredient>> getIngredientes() {
        return mingredientesdao.getIngredientes();
    }

    public LiveData<List<Article>> getnoticias() {
        return mnoticiasDao.getnoticias();
    }

    public void setnombreypas(String nombre, String pas) {
        filtronombre.setValue(nombre);
        filtropassword.setValue(pas);
    }

    public void setnombre(String nombre) {
        filtronombre2.setValue(nombre);

    }

    public LiveData<User> getusers() {
        return Transformations.switchMap(filtronombre, nombre -> {
            return Transformations.switchMap(filtropassword, password -> {
                return musuariosdao.obtenerusuario(nombre, password);
            });
        });

    }

    public LiveData<User> getuser() {
        return Transformations.switchMap(filtronombre2, nombre -> {
            return musuariosdao.existe(nombre);
        });


    }

    public void insertarusuarior(User usuario) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                musuariosdao.insertarUsuario(usuario);
            }

        });
    }

    public LiveData<List<Ingredient>> getalgetingredientesbyIdReceta() {
        return Transformations.switchMap(tipoFilterLiveData, id_receta -> {
            return mingredientesdao.getingredientesbyid_receta(id_receta);
        });
    }


    //Método para poner las fechas, sirve para todos los fragments donde se le llame
    public void setid(String id) {
        tipoFilterLiveData.setValue(id);

    }


    //Devuelve Todos las localizaciones que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Location>> getlocalizaciones() {
        return mlocation.getlocalizaciones();
    }

    //Devuelve Todos los restaurantes que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Result>> getrestaurantes() {
        return mrestaurantes.getrestaurantes();
    }


    public void setfiltrores(String address) {
        filtronamerestaurante.setValue(address);
    }

    public LiveData<Result> getrestaurantesbyname() {
        return Transformations.switchMap(filtronamerestaurante, name -> {
            return mrestaurantes.getrestaurantesbyname(name);
        });
    }

    //Devuelve todas las fotos que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<Photo>> getfotos() {
        return mfotosres.getfotos();
    }


    public LiveData<Photo> getfotobyaddress() {
        return Transformations.switchMap(filtronamerestaurante, address -> {
            return mfotosres.getfotobyres(address);
        });
    }


    public void insertarresenia(Resenia resenia) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mresenia.insertresenia(resenia);
            }

        });
    }

    public LiveData<List<Resenia>> getreseniastotales() {
        return mresenia.getresenias();
    }

    public LiveData<List<Resenia>> getresenias() {
        return Transformations.switchMap(filtronombre, nombre -> {
            return mresenia.obtenerresenia(nombre);
        });
    }

    /**
     * Checks if we have to update the repos data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded(String busqueda) {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get(busqueda);
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mrestaurantes.getNumberRestaurant() == 0;
    }

    public void setbusqueda(String busqueda) {
        filtrobusquda.setValue(busqueda);
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFetchNeeded(busqueda)) {
                dofectchdatosmapa(busqueda); }
        });
    }

    public List<Resenia> getreseniasfirebase() {
               List<Resenia>resenias = new ArrayList<Resenia>();
     firebase.collection("Resenia")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
                                                       String desc = document.getString("descripcion");
                                                       String dir_res = document.getString("dir_res");
                                                       String id_resenia = document.getString("id_resenia");
                                                       String name_res = document.getString("name_res");
                                                       String name_user = document.getString("name_user");
                                                       Double valor = document.getDouble("valor");
                                                       double val = (double) valor;
                                                       Resenia res = new Resenia(id_resenia, name_user, name_res, dir_res, desc, val);
                                                       resenias.add(res);
                                                       Log.d(LOG_TAG, document.getId() + " => " + document.getData());

                                                   }
                                               }
                        else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });

       return resenias;  }




//Insercion de una reseña en la base de datos de firebase

    public void insertarreseniafirebase(Resenia resenia){
        // Add the resenia into firebase
        firebase.collection("Resenia")
                .add(resenia)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(LOG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error adding document", e);
                    }
                });
    }

}











