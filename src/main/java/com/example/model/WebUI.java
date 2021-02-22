package com.example.model;

import com.example.dao.Dao;
import com.example.service.PerfomRequest;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import java.io.File;
import java.io.FileOutputStream;

import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;


@SpringUI
//@Push
public class WebUI extends UI implements  Upload.SucceededListener, Upload.Receiver {
    VerticalLayout  root;
    private Upload upload ;
    HorizontalLayout horizontalLayout;
    private File newFile;

    @Value("${file.path}")
    private String BASE_PATH;


    Grid<Transaction> grid = new Grid<>(Transaction.class);



    @Autowired
    Dao dao;
    @Autowired
    PerfomRequest restService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        System.out.println("Starting web");
        setupLayout();


    }





    private void setupLayout() {
       root = new VerticalLayout();

       VerticalLayout layout = new VerticalLayout();
        upload = new Upload("Upload file", this);

        upload.addSucceededListener(this);
        layout.addComponents(upload);

        root.addComponent(layout);
//        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);

    }

    private void createForm() {

        HorizontalLayout form = new HorizontalLayout();
         TextField name = new TextField();
         Button upload  = new Button("+");
         upload.addStyleName(ValoTheme.BUTTON_FRIENDLY);
         form.addComponents(name,upload);
         root.addComponent(form);

    }

    private void sumbitForm() {
        root.addComponent(new TextArea("Uploaded"));
    }






    @Override
    public OutputStream receiveUpload(String filename,
                                      String mimeType) {

        FileOutputStream fileOutputStream = null; // Stream to write to
        try {

            newFile = new File(BASE_PATH + filename);
            System.out.println(filename);
            fileOutputStream = new FileOutputStream(newFile);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
            return null;
        }
        return fileOutputStream; // Return the output stream to write to
    }



    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        //Do some cool stuff here with the file
        System.out.println("Successed");


        restService.getRequst(); //insert data in tables with post request
        showDataBtn();

    }

    private void showDataBtn(){

        VerticalLayout layout = new VerticalLayout();

        Button showData = new Button("Show data");
        showData.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        showData.addClickListener(clickEvent -> {
            showGrid();
        });

        layout.addComponent(showData);

       root.addComponent(layout);
        root.setSizeFull();
    }

    private void showGrid()  {
        try {
            List<Transaction> transactionList = dao.queryData();
            VerticalLayout layout = new VerticalLayout();
           grid.setItems(transactionList);
            grid.setSelectionMode(Grid.SelectionMode.NONE);
            layout.addComponent(grid);
            grid.setWidth("100%");
           root.addComponent(layout);
           root.addComponent(new VerticalLayout());


        }catch (SQLException e){
            new Notification("Could not fetch data from DB file<br/>",
                    e.getMessage(),
                    Notification.Type.ERROR_MESSAGE)
                    .show(Page.getCurrent());
        }



    }


}
