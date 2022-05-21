package com.patikadev.View;

import com.patikadev.Model.Course;
import com.patikadev.Model.Patika;
import com.patikadev.Model.RegisteredCourse;
import com.patikadev.Model.Student;
import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_stu_top;
    private JLabel lbl_stu_welcome;
    private JTabbedPane pnl_stu_rgstr_course_list;
    private JPanel pnl_stu_patika_list;
    private JScrollPane scrl_stu_patika_list;
    private JTable tbl_stu_patika_list;
    private JButton btn_logout;
    private JPanel pnl_stu_patika_form;
    private JTextField fld_stu_patika_id;
    private JButton btn_stu_get_course_list;
    private JPanel pnl_rgstr_course_list;
    private JScrollPane scr_rgstr_course_list;
    private JTable tbl_rgstr_course_list;
    private JPanel pnl_rgstr_course_refresh;
    private JButton btn_rgstr_course_refresh;


    DefaultTableModel mdl_patika_list = new DefaultTableModel();
    private Object[] row_patika_list;

    DefaultTableModel mdl_rgstr_course_list = new DefaultTableModel();
    private Object[] row_rgstr_course_list;

    private final Student student;

    public StudentGUI(Student student){
        this.student = student;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_stu_welcome.setText("Hoş Geldin " + student.getName());

//patika listesi kodları başlangıcı
        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        tbl_stu_patika_list.setModel(mdl_patika_list);
        tbl_stu_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_stu_patika_list.getColumnModel().getColumn(0).setMaxWidth(75 );

//üzerine tıklanan patikanın id sini ders listele butonu üzerindeki patika ID kutucuğuna yazdırma
        tbl_stu_patika_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_patika_id = tbl_stu_patika_list.getValueAt(tbl_stu_patika_list.getSelectedRow(),0).toString();
                fld_stu_patika_id.setText(select_patika_id);
            }
            catch (Exception exception){

            }
        });
//patika listesi kodları bitişi

//kayıtlı derslerim listesi kodları başlangıcı
        mdl_rgstr_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Dersin Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_rgstr_course_list.setColumnIdentifiers(col_courseList);
        row_rgstr_course_list = new Object[col_courseList.length];
        loadRgstrCourseModel();
        tbl_rgstr_course_list.setModel(mdl_rgstr_course_list);
        tbl_rgstr_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_rgstr_course_list.getTableHeader().setReorderingAllowed(false);



//kayıtlı derslerim listesi kodları başlangıcı

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        btn_stu_get_course_list.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_stu_patika_id)){
                Helper.showMsg("fill");
            }
            else{
                int patika_id = Integer.parseInt(fld_stu_patika_id.getText());
                GetCourseStudentGUI cou = new GetCourseStudentGUI(Integer.parseInt(fld_stu_patika_id.getText()), student);

            }
        });
        btn_rgstr_course_refresh.addActionListener(e -> {
            loadRgstrCourseModel();
        });
    }

    private void loadRgstrCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_rgstr_course_list.getModel();
        clearModel.setRowCount(0);
        ArrayList<Integer> list = new ArrayList<>();
        for (RegisteredCourse c : RegisteredCourse.getList()){
            list.add(c.getCourse_id());
        }

        int i;
        for (Course obj : Course.getList()){

            if (list.contains(obj.getId())){
                i = 0;
                row_rgstr_course_list[i++] = obj.getId();
                row_rgstr_course_list[i++] = obj.getName();
                row_rgstr_course_list[i++] = obj.getLang();
                row_rgstr_course_list[i++] = obj.getPatika().getName();
                row_rgstr_course_list[i++] = obj.getEducator().getName();

                mdl_rgstr_course_list.addRow(row_rgstr_course_list);
            }
        }
    }


    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_stu_patika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }


}
