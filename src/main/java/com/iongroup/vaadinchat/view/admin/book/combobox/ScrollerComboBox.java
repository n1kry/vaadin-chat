package com.iongroup.vaadinchat.view.admin.book.combobox;

import com.iongroup.vaadinchat.entity.LibraryEntity;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScrollerComboBox<T extends LibraryEntity> extends VerticalLayout {

    private ListDataProvider<T> listDataProvider;

    private HorizontalLayout badges;

    private List<T> selected;

    private ComboBox<T> comboBox;

    public ScrollerComboBox(String label, ListDataProvider<T> listDataProvider) {
        this.listDataProvider = listDataProvider;

        badges = new HorizontalLayout();
        selected = new ArrayList<>();

        Scroller scroller = new Scroller(badges);
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setMaxHeight("200px");

        badges.getStyle().set("flex-wrap", "wrap");

        comboBox = new ComboBox<>(label);

        add(comboBox, scroller);
        getStyle().set("max-width", "300px");

        comboBoxConfigurer(this.listDataProvider);
        this.listDataProvider.addFilter(item -> !selected.contains(item));
        this.listDataProvider.refreshAll();
    }

    private void comboBoxConfigurer(ListDataProvider<T> listDataProvider) {
        comboBox.setItemLabelGenerator(T::getName);
        comboBox.setItems(listDataProvider);
        comboBox.setWidthFull();

        comboBox.addValueChangeListener(e -> {
            if (e.getValue() != null && !selected.contains(e.getValue())) {
                T entity = e.getValue();
                selected.add(entity);
                comboBox.clear();
                listDataProvider.refreshAll();
            }
        });
        List<T> removable = new ArrayList<>();
        listDataProvider.addDataProviderListener(e -> {
            badges.removeAll();
            for (T entity: selected) {
                if(listDataProvider.getItems().contains(entity)) {
                    Span badge = createBadge(entity, entity.getName(), selected, listDataProvider);
                    badges.add(badge);
                } else {
                    removable.add(entity);
                }
            }
            selected.removeAll(removable);
            removable.clear();
        });
    }

    private Span createBadge(T entity, String name, List<T> selectedItems, ListDataProvider<T> dataProvider) {
        Button clearButton = new Button(VaadinIcon.CLOSE_SMALL.create());
        clearButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_TERTIARY_INLINE);
        clearButton.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        clearButton.getElement().setAttribute("aria-label", "Clear filter: " + name);
        clearButton.getElement().setAttribute("title", "Clear filter: " + name);

        Span badge = new Span(new Span(name), clearButton);
        badge.getElement().getThemeList().add("badge contrast pill");

        clearButton.addClickListener(event -> {
            badge.getElement().removeFromParent();
            selectedItems.remove(entity);
            dataProvider.refreshAll();
        });

        return badge;
    }

    public void setSelected(List<T> selected) {
        this.selected.clear();
        this.selected.addAll(selected);
        listDataProvider.refreshAll();
    }
}
