package com.iongroup.vaadinchat.view.admin.book;

import com.iongroup.vaadinchat.entity.AuthorEntity;
import com.iongroup.vaadinchat.entity.BookEntity;
import com.iongroup.vaadinchat.entity.GenreEntity;
import com.iongroup.vaadinchat.service.AuthorEntityService;
import com.iongroup.vaadinchat.service.BookEntityService;
import com.iongroup.vaadinchat.service.GenreEntityService;
import com.iongroup.vaadinchat.view.admin.AdminAppLayout;
import com.iongroup.vaadinchat.view.admin.book.combobox.ScrollerComboBox;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.List;

@Route(value = "admin/books", layout = AdminAppLayout.class)
@AnonymousAllowed
public class BookView extends VerticalLayout {

    private ScrollerComboBox<GenreEntity> genreComboBoxLayout;
    private ScrollerComboBox<AuthorEntity> authorComboBoxLayout;
    private BookEntityService bookEntityService;
    private AuthorEntityService authorEntityService;
    private GenreEntityService genreEntityService;
    private Grid<BookEntity> grid;
    private Binder<BookEntity> binder;
    private TextField id;
    private TextField titleField;
    private TextField sourceField;
    private HorizontalLayout authorBadges;
    private HorizontalLayout genreBadges;
    private List<AuthorEntity> selectedAuthors;
    private List<GenreEntity> selectedGenres;
    private ListDataProvider<AuthorEntity> authorDataProvider;
    private ListDataProvider<GenreEntity> genreDataProvider;
    private Dialog dialog;
    private Button addGenreButton;
    private Dialog genreDialog;



    public BookView(BookEntityService bookEntityService,
                    AuthorEntityService authorEntityService,
                    GenreEntityService genreEntityService) {
        this.bookEntityService = bookEntityService;
        this.authorEntityService = authorEntityService;
        this.genreEntityService = genreEntityService;

        binder = new Binder<>(BookEntity.class);

        grid = new Grid<>(BookEntity.class);
        grid.setColumns("title", "authors", "genres", "source");
        grid.getColumns().forEach(column -> column.setResizable(true));

        Button addButton = new Button(LineAwesomeIcon.PLUS_SOLID.create(), event -> dialog.open());
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.getStyle().set("position", "absolute");
        addButton.getStyle().set("right", "20px");
        addButton.getStyle().set("bottom", "20px");


        add(grid, addButton);

        List<BookEntity> bookList = bookEntityService.findAll();

        if (!bookList.isEmpty()) {
            grid.setItems(bookList);
            grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));

            dialogConfigurer();

            binder.forField(id)
                    .withConverter(Long::valueOf, String::valueOf)
                    .bind(BookEntity::getId, BookEntity::setId);
            binder.bind(titleField, "title");
            binder.bind(sourceField, "source");

            genreBadges = genreComboBoxLayout.getBadges();
            selectedGenres = genreComboBoxLayout.getSelected();

            authorBadges = authorComboBoxLayout.getBadges();
            selectedAuthors = authorComboBoxLayout.getSelected();

        }
    }

    private void dialogConfigurer() {
        id = new TextField("Id");
        id.setVisible(false);
        id.setEnabled(false);
        titleField = new TextField("Title");
        sourceField = new TextField("Source");

        authorDataProvider = new ListDataProvider<>(authorEntityService.findAll());
        genreDataProvider = new ListDataProvider<>(genreEntityService.findAll());

        genreComboBoxLayout = new ScrollerComboBox<>("Genre", genreDataProvider);
        authorComboBoxLayout = new ScrollerComboBox<>("Authors", authorDataProvider);

        genreDialog = new GenreViewDialog(genreEntityService, genreDataProvider);

        addGenreButton = new Button("Open genres", event -> genreDialog.open());
        addGenreButton.getStyle().set("margin-left", "10px");

        HorizontalLayout formLayout = new HorizontalLayout(id, titleField, sourceField, genreComboBoxLayout,addGenreButton, authorComboBoxLayout);

        Button saveButton = new Button("Save", event -> saveBook());
        Button deleteButton = new Button("Delete", event -> deleteBook());
        Button closeButton = new Button("Cancel", event -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        dialog = new Dialog(formLayout);
        dialog.getFooter().add(saveButton, deleteButton, closeButton);
        dialog.setCloseOnOutsideClick(true);
        dialog.addDialogCloseActionListener(e -> clearForm());

        formLayout.setWidthFull();
    }

    private void saveBook() {
        BookEntity book;

        if (binder.getBean() == null) {
            book = new BookEntity();
        } else {
            book = binder.getBean();
        }

        book.setTitle(titleField.getValue());
        book.setSource(sourceField.getValue());
        book.setAuthors(selectedAuthors);
        book.setGenres(selectedGenres);

        bookEntityService.save(book);

        refreshGrid();
        clearForm();
    }

    private void deleteBook() {
        BookEntity book = binder.getBean();

        bookEntityService.delete(book);

        refreshGrid();
        clearForm();
    }

    private void editBook(BookEntity book) {
        if (book == null) {
            clearForm();
        } else {
            dialog.open();

            binder.setBean(book);

            genreComboBoxLayout.setSelected(book.getGenres());
            authorComboBoxLayout.setSelected(book.getAuthors());
        }
    }

    private void refreshGrid() {
        grid.setItems(bookEntityService.findAll());
    }

    private void clearForm() {
        binder.setBean(null);
        selectedAuthors.clear();
        selectedGenres.clear();
        authorBadges.removeAll();
        genreBadges.removeAll();
        authorDataProvider.refreshAll();
        genreDataProvider.refreshAll();
        grid.deselectAll();
        dialog.close();
    }
}
