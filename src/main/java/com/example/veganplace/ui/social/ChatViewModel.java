package com.example.veganplace.ui.social;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.veganplace.VeganPlaceRepository;
import com.example.veganplace.data.modelusuario.ChatMessage;

import java.util.List;

public class ChatViewModel extends ViewModel {
    private final VeganPlaceRepository mveganrepository;
    private final LiveData<List<ChatMessage>> mchats;
    private final LiveData<List<ChatMessage>> mchatsconver;





    public ChatViewModel(VeganPlaceRepository repositoriovegano) {
        mveganrepository = repositoriovegano;
        mchats=mveganrepository.getchatreceive();
        mchatsconver=mveganrepository.getconver();

    }


    public void setreceptor(String receptor){ mveganrepository.setreceptor(receptor);}
    public void setemirec(String emisor, String receptor){ mveganrepository.setemisorreceptor(emisor,receptor);}
    public LiveData<List<ChatMessage>> getconver(){return  mveganrepository.getconver();}
    public void actualizar(){mveganrepository.dofetchmensajes();}
    public LiveData<List<ChatMessage>> getsends(){return mveganrepository.getsends();}
    public LiveData<List<ChatMessage>> getreceives(){return mveganrepository.getchatreceive();}


}