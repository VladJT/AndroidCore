package jt.projects.androidcore.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import jt.projects.androidcore.R;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private NotesData dataSource;
    private OnItemClickListener itemClickListener; // Слушатель будет устанавливаться извне

    public NotesListAdapter(NotesData dataSource) {
        this.dataSource = dataSource;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    // Создать новый элемент пользовательского интерфейса (Запускается менеджером)
    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса через Inflater
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_item, parent, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);

    }

    // Заменить данные в пользовательском интерфейсе (Вызывается менеджером)
    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        holder.getTextViewTopic().setText(dataSource.getNote(position).getTopic());
        holder.getTextViewDescription().setText(dataSource.getNote(position).getDescription());
        holder.getTextViewAuthor().setText("Автор: " + dataSource.getNote(position).getAuthor());
        holder.getTextViewDateOfCreation().setText(dataSource.getNote(position).getDateOfCreationAsString());
        //   holder.getImageViewNoteItem().setImageBitmap(dataSource.getNote(position).getTopic());
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTopic;
        private TextView textViewDescription;
        private TextView textViewAuthor;
        private TextView textViewDateOfCreation;
        private ImageView imageViewNoteItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTopic = (TextView) itemView.findViewById(R.id.text_view_notes_item_topic);
            textViewDescription = (TextView) itemView.findViewById(R.id.text_view_notes_item_description);
            textViewAuthor = (TextView) itemView.findViewById(R.id.text_view_notes_item_author);
            textViewDateOfCreation = (TextView) itemView.findViewById(R.id.text_view_notes_item_date_of_creation);
            imageViewNoteItem = (ImageView) itemView.findViewById(R.id.image_view_notes_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public TextView getTextViewTopic() {
            return textViewTopic;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }

        public TextView getTextViewAuthor() {
            return textViewAuthor;
        }

        public TextView getTextViewDateOfCreation() {
            return textViewDateOfCreation;
        }

        public ImageView getImageViewNoteItem() {
            return imageViewNoteItem;
        }
    }

}
