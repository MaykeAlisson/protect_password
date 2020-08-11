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
import br.com.redesenhe.protectpassword.model.Grupo;

public class GrupoListAdapter extends  RecyclerView.Adapter<GrupoListAdapter.MyViewHolder> {

    private List<Grupo> listaGrupo;

    public GrupoListAdapter(List<Grupo> grupos){
        this.listaGrupo = grupos;
    }

    public void reloadList(){
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

        Grupo grupo = listaGrupo.get(position);
        holder.imgViewIconeMundo.setImageResource(R.drawable.icone_mundo);
        holder.txtViewNome.setText(grupo.getNome());
        holder.imgViewIconeKey.setImageResource(R.drawable.icone_key);
    }

    @Override
    public int getItemCount() {
        return listaGrupo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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
