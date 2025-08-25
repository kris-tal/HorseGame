package horsegame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.List;

public abstract class ListPanel<T> extends Panel {

    protected ScrollPane scrollPane;
    protected Table listTable;
    protected T selectedItem;
    protected Table lastSelectedRow = null;

    public ListPanel(float width, float height) {
        super(width, height);

        Label title = UIFactory.createLargeLabel(getTitle());
        content.add(title).padBottom(bigPadding).center().row();

        listTable = new Table();
        scrollPane = new ScrollPane(listTable);
        scrollPane.setFadeScrollBars(false);

        content.add(scrollPane).expand().fill().row();
    }

    protected abstract String getTitle();

    protected abstract void populateRow(Table row, T item);

    protected void onEscapePressed() {}

    protected void onItemSelected(T item, Table row) {
        if (lastSelectedRow != null)
            lastSelectedRow.setColor(Color.WHITE);

        row.setColor(Color.LIGHT_GRAY);
        lastSelectedRow = row;
        selectedItem = item;
    }

    protected void populateList(List<T> items) {
        listTable.clear();
        selectedItem = null;
        lastSelectedRow = null;

        Drawable alternate = UIFactory.createDefaultBackgroundDrawable();
        Drawable defaultBg = UIFactory.createDarkerBackgroundDrawable();

        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            Table row = new Table();

            if (i % 2 == 0) {
                row.setBackground(defaultBg);
            } else {
                row.setBackground(alternate);
            }

            populateRow(row, item);

            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onItemSelected(item, row);
                }
            });

            listTable.add(row).expandX().fillX().pad(5).row();

            if (i == 0) {
                onItemSelected(item, row);
            }
        }
    }
}
