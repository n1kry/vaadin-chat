package com.iongroup.vaadinchat.view.bookviewer;

import com.iongroup.vaadinchat.view.bookviewer.PdfViewer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Route("reader")
@AnonymousAllowed
public class BookReader extends VerticalLayout {

    private PdfViewer pdfViewer;
    private Image currentPageImage;
    private Map<Integer, byte[]> pageCache;
    private Span pageNumberLabel;
    private int currentPage = 1;

    public BookReader() {
        try {
            pdfViewer = new PdfViewer("src/main/resources/static/books/RusanovNichita.pdf");
            pageCache = new HashMap<>();

            int totalPages = pdfViewer.getTotalPages();
            byte[] pageData = getPageData(currentPage);
            StreamResource pageResource = new StreamResource("pageImage.png", () -> new ByteArrayInputStream(pageData));
            currentPageImage = new Image(pageResource, "Page");

            pageNumberLabel = new Span();
            updatePageNumber();

            Button previousPageButton = new Button("Предыдущая страница", event -> previousPage());
            Button nextPageButton = new Button("Следующая страница", event -> nextPage());

            add(currentPageImage, pageNumberLabel, previousPageButton, nextPageButton);
            setAlignItems(Alignment.CENTER);
            setSpacing(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getPageData(int pageNumber) throws IOException {
        byte[] pageData = pageCache.get(pageNumber);
        if (pageData == null) {
            pageData = pdfViewer.renderPage(pageNumber);
            pageCache.put(pageNumber, pageData);
        }
        return pageData;
    }

    private void updatePageNumber() {
        int totalPages = pdfViewer.getTotalPages();
        pageNumberLabel.setText("Текущая страница: " + currentPage + " из " + totalPages);
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            try {
                byte[] pageData = getPageData(currentPage);
                StreamResource pageResource = new StreamResource("pageImage.png", () -> new ByteArrayInputStream(pageData));
                currentPageImage.setSrc(pageResource);
                updatePageNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void nextPage() {
        int totalPages = pdfViewer.getTotalPages();
        if (currentPage < totalPages) {
            currentPage++;
            try {
                byte[] pageData = getPageData(currentPage);
                StreamResource pageResource = new StreamResource("pageImage.png", () -> new ByteArrayInputStream(pageData));
                currentPageImage.setSrc(pageResource);
                updatePageNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}