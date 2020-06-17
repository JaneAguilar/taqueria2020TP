package sample.modelos;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.test.annotations.WrapToTest;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

import javax.swing.text.StyleConstants;
import java.io.IOException;
import java.util.List;

/**
 * Ticket example by niluxer.
 */
@WrapToTest
public class Ticket {

    public static final String logo = "src/imagenes/tacoLOGO.jpeg";

    public void createPdf(String dest, int mesa, String fecha, String mesero, int numOrden, double totalOrden, List listaProd, List listaCantidad) throws IOException {
        //Initialize PDF writer

        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        //Document document = new Document(pdf, pageSize);
        Document document = new Document(pdf);
        document.setMargins(50, 180, 50, 180);


        PdfFont font1 = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font2 = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        Text title =
                new Text("TACOS TEC S.A. DE C.V.").setFont(font1).setFontSize(15);
        Text subtitle = new Text("LAS FUENTES NO.198 \n COL. CENTRO C.P. 38000\n CELAYA, GUANAJUATO \n R.F.C. TAC-68353-TT6").setFont(font2).setFontSize(10);

        Paragraph p1 = new Paragraph().add(title).add("\n").add(subtitle);
        p1.setTextAlignment(TextAlignment.CENTER);

        //DottedLine dottedLine = new DottedLine(1);
        DashedLine dashedLine = new DashedLine(1);

        Text text1 = new Text("COMPROBANTE DE PAGO").setFont(font2).setFontSize(10).setBold();
        Paragraph p2 = new Paragraph().add(text1).add("\n\n");
        p2.setTextAlignment(TextAlignment.CENTER);

        Text text2 = new Text("Mesa: "+mesa).setFont(font2).setFontSize(10);
        Text text3 = new Text("Fecha: "+fecha).setFont(font2).setFontSize(10);
        Text text4 = new Text("Mesero: "+mesero).setFont(font2).setFontSize(10);
        Text text5 = new Text("Orden: " +numOrden).setFont(font2).setFontSize(10);
        Text text6 = new Text("Total de la Orden: "+totalOrden).setFont(font2).setFontSize(10);
        Paragraph p3 = new Paragraph().add(text2).add("\n").add(text3).add("\n").add(text4).add("\n").add(text5).add("\n").add(text6);
        p3.setTextAlignment(TextAlignment.LEFT);

        Image telmex = new Image(ImageDataFactory.create(logo));

        document.add(telmex).add(new Paragraph("\n")).add(p1).add(new LineSeparator(dashedLine)).add(p2).add(p3);

        for (int i = 0; i < listaProd.size(); i++) {
            Text text7 = new Text("Producto: "+listaProd.get(i).toString() + " Cantidad: "+listaCantidad.get(i)).setFont(font2).setFontSize(10);
            Paragraph p4 = new Paragraph().add(text7).add("\n");
            p4.setTextAlignment(TextAlignment.LEFT);
            document.add(new LineSeparator(dashedLine)).add(p4);
        }



        //telmex.setWidth(200).setHeight(100).setTextAlignment(TextAlignment.CENTER);

        //Add paragraph to the document



        //Close document
        document.close();
    }
}