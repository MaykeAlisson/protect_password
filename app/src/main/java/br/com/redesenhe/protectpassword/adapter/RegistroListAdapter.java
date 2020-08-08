package br.com.redesenhe.protectpassword.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.redesenhe.protectpassword.R;
import br.com.redesenhe.protectpassword.model.Registro;

public class RegistroListAdapter extends RecyclerView.Adapter<RegistroListAdapter.MyViewHolder> {

    private List<Registro> listaRegistro;

    public RegistroListAdapter(List<Registro> registros) {
        this.listaRegistro = registros;
    }

    public void reloadList() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.grupo_item, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listaRegistro.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgViewIconeMundo;
        TextView txtViewNome;
        ImageView imgViewIconeKey;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewIconeMundo = itemView.findViewById(R.id.imageViewContasItemIconeMundo);
            txtViewNome = itemView.findViewById(R.id.txtViewItemGrupoNome);
            imgViewIconeKey = itemView.findViewById(R.id.imageViewContasItemIconeKey);

        }
    }
}
