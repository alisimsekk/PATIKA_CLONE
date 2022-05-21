package com.patikadev.View;

import com.patikadev.Model.*;
import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.helper.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JScrollPane scrl_edu_course_list;
    private JTable tbl_edu_course_list;
    private JPanel pnl_edu_top;
    private JLabel lbl_edu_welcome;
    private JButton btn_edu_logout;
    private JTabbedPane tab_operator;
    private JPanel pnl_edu_course_list;
    private JPanel pnl_edu_content_list;
    private JScrollPane scrl_edu_content_list;
    private JPanel pnl_edu_content_form;
    private JTable tbl_edu_content_list;
    private JTextField fld_content_topic;
    private JTextField fld_content_explanation;
    private JTextField fld_ytube_url;
    private JComboBox cmb_content_course;
    private JButton btn_content_add;
    private JTextField fld_content_id;
    private JButton btn_content_delete;
    private JPanel pnl_content_top;
    private JTextField fld_sh_content_topic;
    private JTextField fld_sh_course_name;
    private JButton btn_content_sh;
    private JPanel pnl_edu_quiz_list;
    private JScrollPane scrl_edu_quiz_list;
    private JPanel pnl_quiz_top;
    private JTextField fld_sh_quiz_contTopic;
    private JButton btn_sh_quiz;
    private JTable tbl_edu_quiz_list;
    private JTextField fld_quiz_question;
    private JComboBox cmb_quiz_content;
    private JButton btn_quiz_add;
    private JPanel pnl_edu_quiz_form;
    private JTextField fld_quiz_id_del;
    private JButton btn_quiz_delete;
    private DefaultTableModel mdl_edu_course_list;
    private Object[] row_edu_course_list;
    private DefaultTableModel mdl_edu_content_list;
    private Object[] row_edu_content_list;
    ArrayList<Integer> courseID = new ArrayList<>();
    ArrayList<String> courseName = new ArrayList<>();
    private DefaultTableModel mdl_edu_quiz_list;
    private Object[] row_edu_quiz_list;
    ArrayList<Integer> contentID = new ArrayList<>();

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

        lbl_edu_welcome.setText("Hoş Geldin " + educator.getName());

//Dersler sekmesi kodları başlangıcı
        mdl_edu_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 ||column == 4)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_edu_course_list = {"ID", "Dersin Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_edu_course_list.setColumnIdentifiers(col_edu_course_list);
        row_edu_course_list = new Object[col_edu_course_list.length];
        loadEduCourseModel();
        tbl_edu_course_list.setModel(mdl_edu_course_list);
        tbl_edu_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_edu_course_list.getTableHeader().setReorderingAllowed(false);
//Dersler sekmesi kodları bitişi

//İçerikler sekmesi kodlarının başlangıcı
        mdl_edu_content_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_edu_content_list = {"ID", "İçerik Başlığı", "Açıklama", "Ders Adı", "YouTube Linki"};
        mdl_edu_content_list.setColumnIdentifiers(col_edu_content_list);
        row_edu_content_list = new Object[col_edu_content_list.length];
        loadEduContentModel();
        tbl_edu_content_list.setModel(mdl_edu_content_list);
        tbl_edu_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_edu_content_list.getTableHeader().setReorderingAllowed(false);
        loadCourseCombo();

//üzerine tıklanan içeriğin id sini silme tablosundaki id kutucuğuna yazdırma
        tbl_edu_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_content_id = tbl_edu_content_list.getValueAt(tbl_edu_content_list.getSelectedRow(),0).toString();
                fld_content_id.setText(select_content_id);
            }
            catch (Exception exception){

            }
        });
//İçerikler sekmesi kodlarının bitişi


//Quiz soruları sekmesi kodları başlangıcı
        mdl_edu_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_edu_quiz_list = {"ID", "Quiz Sorusu", "İçerik Başlığı", "İçerik ID"};
        mdl_edu_quiz_list.setColumnIdentifiers(col_edu_quiz_list);
        row_edu_quiz_list = new Object[col_edu_quiz_list.length];
        loadEduQuizModel();
        tbl_edu_quiz_list.setModel(mdl_edu_quiz_list);
        tbl_edu_quiz_list.getColumnModel().getColumn(0).setMaxWidth(50);
        //tbl_edu_quiz_list.getColumnModel().getColumn(2).setMaxWidth(300);
        tbl_edu_quiz_list.getColumnModel().getColumn(3).setMaxWidth(100);
        tbl_edu_quiz_list.getTableHeader().setReorderingAllowed(false);
        loadContentCombo();

        tbl_edu_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_quiz_id = tbl_edu_quiz_list.getValueAt(tbl_edu_quiz_list.getSelectedRow(),0).toString();
                fld_quiz_id_del.setText(select_quiz_id);
            }
            catch (Exception exception){

            }
        });

        tbl_edu_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_content_id = tbl_edu_content_list.getValueAt(tbl_edu_content_list.getSelectedRow(),0).toString();
                fld_content_id.setText(select_content_id);
            }
            catch (Exception exception){

            }
        });
//Quiz soruları sekmesi kodları bitişi

        btn_edu_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        btn_content_add.addActionListener(e -> {
            Item courseItem = (Item) cmb_content_course.getSelectedItem();

            if (Helper.isFieldEmpty(fld_content_topic) || Helper.isFieldEmpty(fld_content_explanation) || Helper.isFieldEmpty(fld_ytube_url)){
                Helper.showMsg("fill");
            }
            else{
                if (Content.add(fld_content_topic.getText(), fld_content_explanation.getText(), courseItem.getKey(), fld_ytube_url.getText())){
                    Helper.showMsg("done");
                    loadEduContentModel();
                    fld_content_topic.setText(null);
                    fld_content_explanation.setText(null);
                    fld_ytube_url.setText(null);
                    loadContentCombo();
                }
                else {
                    Helper.showMsg("error");
                }
            }
        });

        btn_content_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_content_id)){
                Helper.showMsg("fill");
            }
            else {
                Helper.showMsg("delete");
                if (Helper.confirm("sure")){
                    int content_id = Integer.parseInt(fld_content_id.getText());
                    if (Content.delete(content_id)){
                        Helper.showMsg("done");

                        for (Quiz q : Quiz.getList()){
                            if (q.getContent_id() == content_id){
                                Quiz.delete(q.getId());
                            }
                        }
                        loadEduContentModel();
                        fld_content_id.setText(null);
                        loadContentCombo();
                        loadEduQuizModel();
                    }
                    else{
                        Helper.showMsg("error");
                    }
                }
            }
        });

        btn_content_sh.addActionListener(e -> {
            String topic = fld_sh_content_topic.getText();

            if (Helper.isFieldEmpty(fld_sh_content_topic) && Helper.isFieldEmpty(fld_sh_course_name)){
                loadEduContentModel();
            }
            else{
                String query = Helper.searchQuery(topic);
                ArrayList<Content> searchingContent = Content.searchContentList(query);
                loadEduContentModel(searchingContent);
            }
        });

//quiz ekleme
        btn_quiz_add.addActionListener(e -> {
            Item contentItem = (Item) cmb_quiz_content.getSelectedItem();
            if (Helper.isFieldEmpty(fld_quiz_question)){
                Helper.showMsg("fill");
            }
            else{
                if (Quiz.add(fld_quiz_question.getText(), contentItem.getValue(), contentItem.getKey())){
                    Helper.showMsg("done");
                    loadEduQuizModel();
                    fld_quiz_question.setText(null);
                }
                else {
                    Helper.showMsg("error");
                }
            }
        });

//quiz sorusu silme
        btn_quiz_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_id_del)){
                Helper.showMsg("fill");
            }
            else {
                if (Helper.confirm("sure")){
                    int quiz_id = Integer.parseInt(fld_quiz_id_del.getText());
                    if (Quiz.delete(quiz_id)){
                        Helper.showMsg("done");
                        loadEduQuizModel();
                        fld_quiz_id_del.setText(null);
                    }
                    else {
                        Helper.showMsg("error");
                    }
                }
            }
        });

        btn_sh_quiz.addActionListener(e -> {
            String content_topic = fld_sh_quiz_contTopic.getText();
            String query = Helper.searchQuizQuery(content_topic);
            ArrayList <Quiz> searchingQuiz = Quiz.searchQuizList(query);

            loadEduQuizModel(searchingQuiz);
        });

    }

    private void loadEduQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj : Quiz.getList()){
            if (contentID.contains(obj.getContent_id())){
                i = 0;
                row_edu_quiz_list[i++] = obj.getId();
                row_edu_quiz_list[i++] = obj.getQuestion();
                row_edu_quiz_list[i++] = obj.getContent_topic();
                row_edu_quiz_list[i++] = obj.getContent_id();
                mdl_edu_quiz_list.addRow(row_edu_quiz_list);
            }
        }
    }

    private void loadEduQuizModel(ArrayList<Quiz> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj : list){
            if (contentID.contains(obj.getContent_id())){
                i = 0;
                row_edu_quiz_list[i++] = obj.getId();
                row_edu_quiz_list[i++] = obj.getQuestion();
                row_edu_quiz_list[i++] = obj.getContent_topic();
                row_edu_quiz_list[i++] = obj.getContent_id();
                mdl_edu_quiz_list.addRow(row_edu_quiz_list);
            }
        }
    }

    private void loadEduContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj : Content.getList()){
            if (courseID.contains(obj.getCourse_id())){  //educator ın derslerine ait contentleri sorgulama
                i=0;
                row_edu_content_list[i++] = obj.getId();
                contentID.add(obj.getId());    //educator ın kendi içeriklerinin quiz sorularını göstermek için yazıldı
                row_edu_content_list[i++] = obj.getTopic();
                row_edu_content_list[i++] = obj.getExplanation();
                row_edu_content_list[i++] = obj.getCourse().getName();
                row_edu_content_list[i++] = obj.getYtubeUrl();
                mdl_edu_content_list.addRow(row_edu_content_list);
            }
        }
    }

    private void loadEduContentModel(ArrayList<Content> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj : list){
            if (Helper.isFieldEmpty(fld_sh_course_name)){   //ders adına göre filtreleme yoksa
                i=0;
                row_edu_content_list[i++] = obj.getId();
                row_edu_content_list[i++] = obj.getTopic();
                row_edu_content_list[i++] = obj.getExplanation();
                row_edu_content_list[i++] = obj.getCourse().getName();
                row_edu_content_list[i++] = obj.getYtubeUrl();
                mdl_edu_content_list.addRow(row_edu_content_list);
            }
            else{       //ders adına göre filtreleme olacaksa
                for (String name : courseName){     //içerik listesinde direkt ders adı olmadığı için ders adları ayrı listede tutuldu
                    if (name.contains(fld_sh_course_name.getText()) && name.equals(obj.getCourse().getName())){  //ders adı listesinde filtre karakteri var mı sorgusu ve ders adlarının eşitliği sorgusu
                        i=0;
                        row_edu_content_list[i++] = obj.getId();
                        row_edu_content_list[i++] = obj.getTopic();
                        row_edu_content_list[i++] = obj.getExplanation();
                        row_edu_content_list[i++] = obj.getCourse().getName();
                        row_edu_content_list[i++] = obj.getYtubeUrl();
                        mdl_edu_content_list.addRow(row_edu_content_list);
                    }
                }
            }

        }
    }

    private void loadEduCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_edu_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Course obj : Course.getListByUser(educator.getId())){
            i = 0;
            row_edu_course_list[i++] = obj.getId();
            courseID.add(obj.getId());      //educator ın derslerine ait contentleri listelemek için courseID arraylisti kullanıldı
            row_edu_course_list[i++] = obj.getName();
            courseName.add(obj.getName());  //contentleri filtrelemek için kullanılacak
            row_edu_course_list[i++] = obj.getLang();
            row_edu_course_list[i++] = obj.getPatika().getName();
            row_edu_course_list[i++] = obj.getEducator().getName();

            mdl_edu_course_list.addRow(row_edu_course_list);

        }
    }

//İçerikler sekmesinde ders combo box ına verileri aktaran metod
    public void loadCourseCombo(){
        cmb_content_course.removeAllItems();
        for (Course obj : Course.getList()){
            if (obj.getUser_id() == educator.getId()){
                cmb_content_course.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }


//Quiz sekmesinde içerikler combo box ına verileri aktaran metod
    private void loadContentCombo() {
        cmb_quiz_content.removeAllItems();
        for (Content obj : Content.getList()){
            if (courseID.contains(obj.getCourse_id())){
                cmb_quiz_content.addItem(new Item(obj.getId(), obj.getTopic()));
            }
        }

    }


}
