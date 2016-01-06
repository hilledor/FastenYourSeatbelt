/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchers;

import java.util.List;
import javafx.collections.ObservableList;
import model.User;

/**
 *
 * @author jdor
 */
interface searcher {
     public  ObservableList<User> searchCombo(String text);
     public String getWhereClause( List<Object> params, String searchText);
}
