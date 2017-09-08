/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sample;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Администратор
 */
public class DataModule extends AbstractListModel<Employee> {

    private List<Employee> data = new ArrayList();

    public void add(Employee e) {
        data.add(e);
        fireIntervalAdded(this, data.size() - 1, data.size());
    }

    public void addAll(List<Employee> list) {
        data.addAll(list);
        fireIntervalAdded(this, data.size() - list.size(), data.size());
    }

    public void removeAll() {
        data.clear();
        fireIntervalRemoved(this, 0, data.size());
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Employee getElementAt(int index) {
        return data.get(index);
    }

}
