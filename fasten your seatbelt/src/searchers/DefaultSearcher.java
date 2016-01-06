/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchers;

import java.util.List;
import javafx.collections.ObservableList;
import model.User;
import utils.Utils;

/**
 *
 * @author jdor
 */
public abstract class DefaultSearcher implements searcher {

    String searchText = "";

    public String[] getSearchStrings() {
        if (Utils.isEmpty(searchText)) {
            return new String[0];
        }
        String[] retString = searchText.split(" ");
        return retString;
    }

    public int[] getSearchInts() {
        String[] strings = searchText.split(" ");
        String newIntString = "";
        for (int i = 0; i < strings.length; i++) {
            try {
                int isInt = Integer.parseInt(strings[i]);
                newIntString = Utils.glue(newIntString, "" + isInt, " ");
            } catch (NumberFormatException e) {
            }
        }
        if (Utils.isEmpty(newIntString)) {
            return new int[0];
        }
        String[] sInts = newIntString.split(" ");

        System.out.println("sInts " + sInts.length);
        int[] ints = new int[sInts.length];
        for (int i = 0; i < sInts.length; i++) {
            ints[i] = Integer.parseInt(sInts[i]);
        }
        return ints;
    }

}
