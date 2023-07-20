package com.iongroup.vaadinchat.view.bookviewer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PdfViewer {
    private final PDDocument document;
    private final PDFRenderer renderer;

    public PdfViewer(String filePath) throws IOException {
        File file = new File(filePath);
        document = PDDocument.load(file);
        renderer = new PDFRenderer(document);
    }

    public int getTotalPages() {
        return document.getNumberOfPages();
    }

    public byte[] renderPage(int pageNumber) throws IOException {
        BufferedImage image = renderer.renderImageWithDPI(pageNumber - 1, 96, ImageType.RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
