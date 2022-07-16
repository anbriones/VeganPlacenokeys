package com.example.veganplace;


import android.util.Log;

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
import com.example.veganplace.data.modelusuario.User;
import com.example.veganplace.data.roomdatabase.DaoIngrediente;
import com.example.veganplace.data.roomdatabase.DaoLocation;
import com.example.veganplace.data.roomdatabase.DaoNoticia;
import com.example.veganplace.data.roomdatabase.DaoReceta;
import com.example.veganplace.data.roomdatabase.DaoResult;
import com.example.veganplace.data.roomdatabase.DaoUsuario;

import java.util.Arrays;
import java.util.List;

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


    private final RecetasNetworkDataSource mRecetaNetworkDataSource;
    private final IngredienteNetworkDataSource mIngretaNetworkDataSource;
    private final NoticiasNetWorkDatasource mNoticiasNetworkDataSource;
    private final RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource;
    private final CoordenadasNetWorkDatasource mLocationNetworkdataSource;

    private final AppExecutors mExecutors = AppExecutors.getInstance();

    private final MutableLiveData<String> tipoFilterLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> filtronombre = new MutableLiveData<>();
    private final MutableLiveData<String> filtronombre2 = new MutableLiveData<>();
    private final MutableLiveData<String> filtropassword = new MutableLiveData<>();
    private final MutableLiveData<String> filtronamerestaurante = new MutableLiveData<>();

    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private VeganPlaceRepository(DaoReceta mrecetasdao, DaoIngrediente mingredientesdao, DaoNoticia mnoticiasDao,DaoUsuario muserdao,DaoResult mrestaurantes, DaoLocation mlocation, IngredienteNetworkDataSource mIngretaNetworkDataSource, RecetasNetworkDataSource mRecetaNetworkDataSource1, NoticiasNetWorkDatasource mNoticiasNetworkDataSource, RestaurantesNetWorkDatasource mRestaurantesNetwoekdataSource, CoordenadasNetWorkDatasource mCoordenadasNetworkDtaSource) {
        this.mrecetasdao = mrecetasdao;
        this.mingredientesdao = mingredientesdao;
        this.mnoticiasDao=mnoticiasDao;
        this.musuariosdao=muserdao;
        this.mrestaurantes=mrestaurantes;
        this.mlocation = mlocation;
        this.mRecetaNetworkDataSource = mRecetaNetworkDataSource1;
        this.mIngretaNetworkDataSource = mIngretaNetworkDataSource;
        this.mNoticiasNetworkDataSource=mNoticiasNetworkDataSource;
        this.mRestaurantesNetwoekdataSource=mRestaurantesNetwoekdataSource;
        this.mLocationNetworkdataSource= mCoordenadasNetworkDtaSource;


        dofectchdatos();
        // LiveData that fetches alimentosjson from network
        LiveData<Recipe[]> networkData = this.mRecetaNetworkDataSource.getCurrentrecetas();
        LiveData<Ingredient[]> networkDataing = this.mIngretaNetworkDataSource.getcurrentingredientes();
        LiveData<Article[]> networkNoticias= this.mNoticiasNetworkDataSource.getcurrentnoticias();
        LiveData<Result[]> networkRestaurantes = this.mRestaurantesNetwoekdataSource.getcurrentrestaurantes();
        LiveData<Location[]> networkLocation = this.mLocationNetworkdataSource.getcurrentlocalizaciones();



        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newRecetasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mrecetasdao.insertarReceta(Arrays.asList(newRecetasFromNetwork));

                Log.d(LOG_TAG, "New values recipes inserted in Room");
            });
        });

        networkDataing.observeForever(newIngreFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mingredientesdao.insertarIngrediente(Arrays.asList(newIngreFromNetwork));
                Log.d(LOG_TAG, "New values ingredients inserted in Room");
            });
        });


        networkNoticias.observeForever(newNoticiasFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mnoticiasDao.insertarNoticia(Arrays.asList(newNoticiasFromNetwork));
                Log.d(LOG_TAG, "New values news inserted in Room");
            });
        });


        networkRestaurantes.observeForever(newRestauranteFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mrestaurantes.insertarRestaurante(Arrays.asList(newRestauranteFromNetwork));
                Log.d(LOG_TAG, "New values results  inserted in Room");
            });
        });

        networkLocation.observeForever(newLocalizacionFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                mlocation.insertarLocation(Arrays.asList(newLocalizacionFromNetwork));
                Log.d(LOG_TAG, "New values locations  inserted in Room");
            });
        });


    }

    public synchronized static VeganPlaceRepository getInstance(RecetasNetworkDataSource nds, IngredienteNetworkDataSource ndsi, NoticiasNetWorkDatasource ndn, RestaurantesNetWorkDatasource ndr, CoordenadasNetWorkDatasource ndl, DaoReceta daoReceta, DaoIngrediente daoIngrediente,DaoNoticia daoNoticia,DaoUsuario daoUsuario, DaoResult daoresult, DaoLocation daoLocation) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new VeganPlaceRepository(daoReceta, daoIngrediente,daoNoticia,daoUsuario,daoresult,daoLocation, ndsi, nds,ndn,ndr,ndl);//Pasa las dependencias por constructor
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

        Log.d(LOG_TAG, "Fetching result(restaurantes)  from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mrestaurantes.eliminarrestaurantes();
            mRestaurantesNetwoekdataSource.fetchrestaurantes();
        });


        Log.d(LOG_TAG, "Fetching localizaciones from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mlocation.eliminarLocalizaciones();
            mLocationNetworkdataSource.fetchlocation();
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



    public void setnombreypas(String nombre,String pas){
        filtronombre.setValue(nombre);
        filtropassword.setValue(pas);
    }

    public void setnombre(String nombre){
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

    public void insertarusuarior(User usuario){
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


    public void setfiltrores(String address){
        filtronamerestaurante.setValue(address);
    }

    public LiveData<Result> getrestaurantesbyname() {
        return Transformations.switchMap(filtronamerestaurante, name -> {
            return mrestaurantes.getrestaurantesbyname(name);
        });
    }

}









