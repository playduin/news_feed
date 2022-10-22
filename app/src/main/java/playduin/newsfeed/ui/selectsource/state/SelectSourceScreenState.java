package playduin.newsfeed.ui.selectsource.state;

import java.util.List;

import playduin.newsfeed.ui.mvi.ScreenState;
import playduin.newsfeed.ui.selectsource.SelectSourceContract;
import playduin.newsfeed.models.news.SourceItem;

public class SelectSourceScreenState extends ScreenState<SelectSourceContract.View> {
    private static final int SET_ITEMS = 1;

    private final int action;
    private final List<SourceItem> itemList;

    public SelectSourceScreenState(int action, List<SourceItem> itemList) {
        this.action = action;
        this.itemList = itemList;
    }

    public static SelectSourceScreenState createSetItemsState(List<SourceItem> itemList) {
        return new SelectSourceScreenState(SET_ITEMS, itemList);
    }

    public void visit(SelectSourceContract.View selectSourceView) {
        if (SET_ITEMS == action) {
            selectSourceView.setItems(itemList);
        }
    }
}
