package jt.projects.androidcore.notes;

import android.view.View;

// Интерфейс для обработки нажатий, как в ListView
public interface OnItemClickListener {
    void onItemClick(View view, int position);
}