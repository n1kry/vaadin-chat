package com.iongroup.vaadinchat.view.admin.book;

import com.iongroup.vaadinchat.entity.GenreEntity;
import com.iongroup.vaadinchat.service.GenreEntityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.vaadin.lineawesome.LineAwesomeIcon;


public class GenreViewDialog extends Dialog {
    private Grid<GenreEntity> grid;
    private TextField genreInput;
    private Button addGenreButton;
    private HorizontalLayout saveGenreButton;
    private HorizontalLayout creatingLayout;
    private GenreEntity selected;

    public GenreViewDialog(GenreEntityService genreEntityService, ListDataProvider<GenreEntity> listDataProvider) {
        genreInput = new TextField("Genre Name");

        grid = new Grid<>(GenreEntity.class);
        grid.setColumns("name");
        grid.setItems(genreEntityService.findAll());

        grid.addComponentColumn(genre -> createButtonLayout(genre, genreEntityService, listDataProvider))
                .setHeader("Actions").setFlexGrow(0).setWidth("130px").getElement().setAttribute("justify-self", "end");

        addGenreButton = getAddButton(genreEntityService, listDataProvider);
        creatingLayout = new HorizontalLayout(genreInput, addGenreButton);
        creatingLayout.setAlignItems(FlexComponent.Alignment.END);

        saveGenreButton = getSaveButton(genreEntityService, listDataProvider);

        VerticalLayout genreLayout = new VerticalLayout(creatingLayout, grid);
        genreLayout.setSpacing(false);
        genreLayout.setPadding(false);

        add(genreLayout);
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setWidth("450px");
        setResizable(true);

        getFooter().add(getCancelButton());
    }

    private HorizontalLayout createButtonLayout(GenreEntity genre, GenreEntityService genreEntityService, ListDataProvider<GenreEntity> listDataProvider) {
        Button deleteButton = new Button(LineAwesomeIcon.TRASH_SOLID.create(), event -> {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Delete \"" + genre + "\"?");
            dialog.setText("Are you sure you want to permanently delete this item?");
            dialog.setCancelable(true);
            dialog.setConfirmText("Delete");
            dialog.setConfirmButtonTheme("error primary");
            dialog.addConfirmListener(e -> {
                genreEntityService.delete(genre);
                grid.setItems(genreEntityService.findAll());
                listDataProvider.getItems().remove(genre);
                listDataProvider.refreshAll();
                Notification.show(genre + " deleted!", 3000, Notification.Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
            dialog.open();
        });

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        Button editButton = new Button(LineAwesomeIcon.PEN_SOLID.create(), event -> {
            genreInput.setValue(genre.getName());
            creatingLayout.remove(addGenreButton);
            selected = genre;
            creatingLayout.add(saveGenreButton);
        });

        return new HorizontalLayout(deleteButton, editButton);
    }

    private Button getCancelButton() {
        return new Button("Cancel", event -> {
            genreInput.clear();
            close();
        });
    }

    private Button getAddButton(GenreEntityService genreEntityService, ListDataProvider<GenreEntity> listDataProvider) {
        Button button = new Button("Add", LineAwesomeIcon.PLUS_SOLID.create(), event -> {
            String genreName = genreInput.getValue();
            if (!genreName.isEmpty()) {
                GenreEntity newGenre = new GenreEntity();
                newGenre.setName(genreName);
                newGenre = genreEntityService.getDao().save(newGenre);
                grid.setItems(genreEntityService.findAll());
                listDataProvider.refreshAll();
                genreInput.clear();
                listDataProvider.getItems().add(newGenre);
                Notification.show(newGenre + " added!", 3000, Notification.Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        });
        button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        return button;
    }

    private HorizontalLayout getSaveButton(GenreEntityService genreEntityService, ListDataProvider<GenreEntity> listDataProvider) {
        HorizontalLayout saveLayout = new HorizontalLayout();
        Button saveButton = new Button("Save", LineAwesomeIcon.SAVE.create(), event -> {
            String genreName = genreInput.getValue();
            if (!genreName.isEmpty()) {
                selected.setName(genreName);
                genreEntityService.getDao().save(selected);
                grid.setItems(genreEntityService.findAll());
                listDataProvider.refreshAll();
                genreInput.clear();
                creatingLayout.remove(saveLayout);
                creatingLayout.add(addGenreButton);
                listDataProvider.refreshAll();
                Notification.show(selected + " changed!", 3000, Notification.Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button closeButton = new Button(LineAwesomeIcon.CLOSED_CAPTIONING.create(), event -> {
            creatingLayout.remove(saveLayout);
            creatingLayout.add(addGenreButton);
        });
        saveLayout.add(saveButton, closeButton);
        return saveLayout;
    }
}
