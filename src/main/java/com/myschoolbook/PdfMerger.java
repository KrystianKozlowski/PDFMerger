package com.myschoolbook;

/**
 * Created by Krystian on 2017-06-29.
 */
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFMergerUtility;

public class PdfMerger {
    public void combine(List<String> fileList, String mergedFilePath){
        try{
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            for (String filePath : fileList){
                mergePdf.addSource(filePath);
            }
            mergePdf.setDestinationFileName(mergedFilePath);
            mergePdf.mergeDocuments();
        }
        catch(Exception e)
        {

        }
    }

    public void createNew()
    {
        PDDocument document = null;
        try
        {
            String filename="";
            document=new PDDocument();
            PDPage blankPage = new PDPage();
            document.addPage( blankPage );
            document.save( filename );
        }
        catch(Exception e)
        {

        }
    }

}
