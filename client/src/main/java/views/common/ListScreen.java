//package views.common;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.*;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//
//import java.util.List;
//
//public abstract class ListScreen<T> extends ScreenAdapter {
//    protected Stage stage;
//    protected Table mainTable;
//    protected ScrollPane scrollPane;
//    protected Table listTable;
//    protected T selectedItem;
//    protected Table lastSelectedRow = null;
//    protected Skin skin;
//
//    public ListScreen(Skin skin) {
//        stage = new Stage(new ScreenViewport());
//        this.skin = skin;
//
//        mainTable = new Table();
//        mainTable.setFillParent(true);
//        stage.addActor(mainTable);
//
//        listTable = new Table();
//        scrollPane = new ScrollPane(listTable, skin);
//        scrollPane.setFadeScrollBars(false);
//
//        mainTable.top().left();
//        mainTable.add(new Label(getTitle(), skin, "default")).colspan(2).pad(10);
//        mainTable.row();
//        mainTable.add(scrollPane).expand().fill().colspan(2).pad(10);
//
//        Gdx.input.setInputProcessor(stage);
//
//        stage.addListener(new InputListener() {
//            @Override
//            public boolean keyDown(InputEvent event, int keycode) {
//                if (keycode == Input.Keys.ESCAPE) {
//                    onEscapePressed();
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void render(float delta) {
//        stage.act(delta);
//        stage.draw();
//    }
//
//    @Override
//    public void dispose() {
//        stage.dispose();
//        skin.dispose();
//    }
//
//    protected abstract String getTitle();
//    protected abstract void populateRow(Table row, T item);
//    protected void onEscapePressed() { }
//
//    protected void onItemSelected(T item, Table row) {
//        if (lastSelectedRow != null)
//            lastSelectedRow.setColor(Color.WHITE);
//
//        row.setColor(Color.LIGHT_GRAY);
//        lastSelectedRow = row;
//        selectedItem = item;
//    }
//
//    protected void populateList(List<T> items) {
//        listTable.clear();
//        selectedItem = null;
//        lastSelectedRow = null;
//
//        Drawable alternate = skin.newDrawable("white", Color.LIGHT_GRAY);
//        Drawable defaultBg = skin.newDrawable("white", Color.WHITE);
//
//        for (int i = 0; i < items.size(); i++) {
//            T item = items.get(i);
//            Table row = new Table(skin);
//            // zebra pattern
//            if (i % 2 == 0) {
//                row.setBackground(defaultBg);
//            } else {
//                row.setBackground(alternate);
//            }
//            populateRow(row, item);
//
//            row.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    onItemSelected(item, row);
//                }
//            });
//
//            listTable.add(row).expandX().fillX().pad(2).row();
//
//            if (i == 0) {
//                onItemSelected(item, row);
//            }
//        }
//    }
//}
