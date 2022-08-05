package com.example.veganplace;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.veganplace.data.lecturaapinoticias.NoticiasNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.CoordenadasNetWorkDatasource;
import com.example.veganplace.data.lecturamapas.RestaurantesNetWorkDatasource;
import com.example.veganplace.data.lecturarectas.IngredienteNetworkDataSource;
import com.example.veganplace.data.lecturarectas.RecetasNetworkDataSource;
import com.example.veganplace.data.modelmapas.Location;
import com.example.veganplace.data.modelmapas.Result;
import com.example.veganplace.data.modelnoticias.Article;
import com.example.veganplace.data.modelrecetas.Ingredient;
import com.example.veganplace.data.modelrecetas.Recipe;
import com.example.veganplace.data.modelusuario.Resenia;
import com.example.veganplace.data.roomdatabase.DaoChats;
import com.example.veganplace.data.roomdatabase.DaoIngrediente;
import com.example.veganplace.data.roomdatabase.DaoLocation;
import com.example.veganplace.data.roomdatabase.DaoNoticia;
import com.example.veganplace.data.roomdatabase.DaoReceta;
import com.example.veganplace.data.roomdatabase.DaoResult;
import com.example.veganplace.data.modelusuario.ChatMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class VeganPlaceRepository extends AppCompatActivity {
    private static final String LOG_TAG = VeganPlaceRepository.class.getSimpleName();
    private Boolean sepuede=false;

    // For Singleton instantiation
    private static VeganPlaceRepository sInstance;
    private final DaoReceta mrecetasdao;
    private final DaoIngrediente mingredientesdao;
    private final DaoNoticia mnoticiasDao;
    private final DaoResult mrestaurantes;
    private final DaoLocation mlocation;
    private final DaoChats mchats;

FirebaseFirestore db=FirebaseFirestore.getInstance();

    private final RecetasNetworkDataSource mRecetaNetworkDataSource;
    private final IngredienteNetworkDataSource mIngretaNetworkDataSource;
    private final NoticiasNetWorkDatasource mNoticiasNetworkDataSource;
    private final RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource;
    private final CoordenadasNetWorkDatasource mLocationNetworkdataSource;

    private final AppExecutors mExecutors = AppExecutors.getInstance();

    private final MutableLiveData<String> tipoFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> filtronamerestaurante = new MutableLiveData<>();
    private final MutableLiveData<String> filtrobusquda = new MutableLiveData<>();
    private final MutableLiveData<String> filtroemisor = new MutableLiveData<>();
    private final MutableLiveData<String> filtroreceptor = new MutableLiveData<>();

    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();

    private VeganPlaceRepository(DaoReceta mrecetasdao, DaoIngrediente mingredientesdao, DaoNoticia mnoticiasDao, DaoResult mrestaurantes, DaoLocation mlocation, DaoChats mchats,  IngredienteNetworkDataSource mIngretaNetworkDataSource, RecetasNetworkDataSource mRecetaNetworkDataSource1, NoticiasNetWorkDatasource mNoticiasNetworkDataSource, RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource, CoordenadasNetWorkDatasource mCoordenadasNetworkDtaSource ) {
        this.mrecetasdao = mrecetasdao;
        this.mingredientesdao = mingredientesdao;
        this.mnoticiasDao = mnoticiasDao;
        this.mrestaurantes = mrestaurantes;
        this.mlocation = mlocation;
        this.mchats=mchats;

        this.mRecetaNetworkDataSource = mRecetaNetworkDataSource1;
        this.mIngretaNetworkDataSource = mIngretaNetworkDataSource;
        this.mNoticiasNetworkDataSource = mNoticiasNetworkDataSource;
        this.mRestaurantesNetwoekdataSource = mRestaurantesNetwoekdataSource;
        this.mLocationNetworkdataSource = mCoordenadasNetworkDtaSource;

        dofectchdatos();
        dofetchmensajes();


        //
        // LiveData that fetches alimentosjson from network
        LiveData<Recipe[]> networkData = this.mRecetaNetworkDataSource.getCurrentrecetas();
        LiveData<Ingredient[]> networkDataing = this.mIngretaNetworkDataSource.getcurrentingredientes();
        LiveData<Article[]> networkNoticias = this.mNoticiasNetworkDataSource.getcurrentnoticias();
        LiveData<Result[]> networkRestaurantes = this.mRestaurantesNetwoekdataSource.getcurrentrestaurantes();
        LiveData<Location[]> networkLocation = this.mLocationNetworkdataSource.getcurrentlocalizaciones();
       // LiveData<ChatMessage[]> networkChat = this.mchatsNetworkdatasource.getcurrentChats();


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



    }

    public synchronized static VeganPlaceRepository getInstance(RecetasNetworkDataSource nds, IngredienteNetworkDataSource ndsi, NoticiasNetWorkDatasource ndn, RestaurantesNetWorkDatasource ndr, CoordenadasNetWorkDatasource ndl, DaoReceta daoReceta, DaoIngrediente daoIngrediente, DaoNoticia daoNoticia,  DaoResult daoresult, DaoLocation daoLocation , DaoChats daochats) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new VeganPlaceRepository(daoReceta, daoIngrediente, daoNoticia,  daoresult, daoLocation,daochats, ndsi, nds, ndn, ndr, ndl);//Pasa las dependencias por constructor
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }


    public void dofetchmensajes(){
        db.collection("ChatMessage")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String emisor = document.getString("emisor");
                                String receptor = document.getString("receptor");
                                String texto = document.getString("texto");
                                String messageTime = document.getString("messageTime");

                                ChatMessage chat = new ChatMessage(" ", emisor, receptor, texto, messageTime);

                                insertarchat(chat);
                            }

                        }else {
                            Log.d(LOG_TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




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
    }

    public void insertarchat(ChatMessage chat) {
        mExecutors.diskIO().execute(() -> {
            mchats.insertarchat(chat);
        });
    }

      public void setreceptor(String receptor) {
        filtroreceptor.setValue(receptor);
    }

    public void setemisorreceptor(String emisor, String receptor){
        filtroemisor.setValue(receptor);
        filtroemisor.setValue(emisor);
    }

    public  LiveData<List<ChatMessage>> getconver(){
        return Transformations.switchMap(filtroemisor, input -> Transformations.switchMap(filtroreceptor, input2 -> mchats.getchats(input, input2)));

    }

    public LiveData<List<ChatMessage>> getchatreceive() {
        return Transformations.switchMap(filtroreceptor, receptor -> {
            return mchats.getchatsreeceive(receptor);
        });
    }


    public LiveData<List<ChatMessage>> getsends() {
        return Transformations.switchMap(filtroreceptor, receptor -> {
            return mchats.getchatssend(receptor);
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

                dofectchdatosmapa(busqueda);
        });
    }



    // --- MÉTODOOS DE LA BASE DE DATOS FIREBASE

/*
Insercion de una reseña en la base de datos de firebase
 */

    public void insertarreseniafirebase(Resenia resenia){
         FirebaseFirestore firebase=FirebaseFirestore.getInstance();
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











