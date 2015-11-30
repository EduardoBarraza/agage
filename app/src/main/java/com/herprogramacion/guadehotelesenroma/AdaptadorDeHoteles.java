package com.herprogramacion.guadehotelesenroma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} para poblar un recycler view
 * con información de hoteles.
 */
public class AdaptadorDeHoteles extends RecyclerView.Adapter<AdaptadorDeHoteles.ViewHolder> {

    /**
     * Interfaz de comunicación
     */
    public interface OnItemClickListener {
        void onItemClick(ViewHolder item, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public RatingBar valoracion;
        public ImageView imagen;

        private AdaptadorDeHoteles padre = null;

        public ViewHolder(View v, AdaptadorDeHoteles padre) {
            super(v);

            v.setOnClickListener(this);
            this.padre = padre;

            nombre = (TextView) v.findViewById(R.id.nombre_hotel);
            precio = (TextView) v.findViewById(R.id.precio_actual);
            valoracion = (RatingBar) v.findViewById(R.id.calificacion);
            imagen = (ImageView) v.findViewById(R.id.miniatura_hotel);

        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = padre.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return Hotel.HOTELES.get(position).getIdHotel();
    }

    public AdaptadorDeHoteles() {
    }

    @Override
    public int getItemCount() {
        return Hotel.HOTELES.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hotel, viewGroup, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Hotel item = Hotel.HOTELES.get(i);

        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("$" + item.getPrecio());
        viewHolder.valoracion.setRating(item.getCalificacion());
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrlImagen())
                .centerCrop()
                .into(viewHolder.imagen);

    }


}
