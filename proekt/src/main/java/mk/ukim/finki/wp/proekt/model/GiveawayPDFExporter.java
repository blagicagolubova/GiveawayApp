package mk.ukim.finki.wp.proekt.model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class GiveawayPDFExporter {

    private List<Giveaway> giveawayList;

    public GiveawayPDFExporter(List<Giveaway> giveawayList) {
        this.giveawayList = giveawayList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Start Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("End Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Status", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Award", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Creator", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Giveaway giveaway : giveawayList) {
            table.addCell(String.valueOf(giveaway.getName()));
            table.addCell(String.valueOf(giveaway.getStartDate()));
            table.addCell(String.valueOf(giveaway.getEndDate()));
            table.addCell(giveaway.getStatus().toString());
            table.addCell(giveaway.getAward().getName());
            table.addCell(giveaway.getCategory().getName());
            table.addCell(giveaway.getCreator().getName());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(14);
        font.setColor(Color.DARK_GRAY);

        Paragraph p = new Paragraph("List of Giveaways", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.5f, 2.5f, 2.0f, 2.0f,2.0f,1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }


}