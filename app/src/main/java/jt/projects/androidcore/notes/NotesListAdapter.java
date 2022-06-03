package jt.projects.androidcore.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import jt.projects.androidcore.R;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private NotesData dataSource;
    private OnItemClickListener itemClickListener; // Слушатель будет устанавливаться извне
    private final NotesListFragment fragment; // фрагмент, обрабатывающий контекстное меню
    private int menuPosition; // Поле menuPosition будет хранить элемент, на котором вызывается контекстное меню

    public int getMenuPosition() {
        return menuPosition;
    }

    public NotesListAdapter(NotesData dataSource, NotesListFragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
        private ImageButton buttonContextMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTopic = (TextView) itemView.findViewById(R.id.text_view_notes_item_topic);
            textViewDescription = (TextView) itemView.findViewById(R.id.text_view_notes_item_description);
            textViewAuthor = (TextView) itemView.findViewById(R.id.text_view_notes_item_author);
            textViewDateOfCreation = (TextView) itemView.findViewById(R.id.text_view_notes_item_date_of_creation);
            imageViewNoteItem = (ImageView) itemView.findViewById(R.id.image_view_notes_item);

            // регистрируем контекстное меню
            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuPosition = getLayoutPosition();
                    }
                });
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            buttonContextMenu = itemView.findViewById(R.id.button_notes_item_context_menu);
            // контекстное меню
            buttonContextMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuPosition = getLayoutPosition();
                    int x = buttonContextMenu.getLeft();
                    int y = buttonContextMenu.getTop();
                    itemView.showContextMenu(x, y);
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
