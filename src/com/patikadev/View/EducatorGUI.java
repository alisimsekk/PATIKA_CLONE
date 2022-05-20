package com.patikadev.View;

import com.patikadev.Model.Course;
import com.patikadev.Model.Educator;
import com.patikadev.Model.User;
import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JScrollPane scrl_edu_course_list;
    private JTable tbl_edu_course_list;
    private JPanel pnl_edu_top;
    private JLabel lbl_edu_welcome;
    private JButton btn_edu_logout;
    private DefaultTableModel mdl_edu_course_list;
    private Object[] row_edu_course_list;

    private final Educator educator;


    public EducatorGUI (Educator educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x" , getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_edu_welcome.setText("Hoşgeldin " + educator.getName());

        mdl_edu_course_list = new DefaultTableModel();
        Object[] col_edu_course_list = {"ID", "Dersin Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_edu_course_list.setColumnIdentifiers(col_edu_course_list);
        row_edu_course_list = new Object[col_edu_course_list.length];
        loadEduCourseModel();
        tbl_edu_course_list.setModel(mdl_edu_course_list);
        tbl_edu_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_edu_course_list.getTableHeader().setReorderingAllowed(false);




        btn_edu_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });
    }

    private void loadEduCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj : Course.getListByUser(educator.getId())){
            i = 0;
            row_edu_course_list[i++] = obj.getId();
            row_edu_course_list[i++] = obj.getName();
            row_edu_course_list[i++] = obj.getLang();
            row_edu_course_list[i++] = obj.getPatika().getName();
            row_edu_course_list[i++] = obj.getEducator().getName();

            mdl_edu_course_list.addRow(row_edu_course_list);



        }
    }


}
