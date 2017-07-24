package apiClass;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

public class QRScan {
	
	public static String scanQR(String filePath)
	{
		String charset = "UTF-8"; // or "ISO-8859-1"
		String resultQR = "";
		Map<DecodeHintType,Object> hintMap = new HashMap<DecodeHintType, Object>();
		hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		hintMap.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
		Map<DecodeHintType,Object> hintPure = new EnumMap<>(hintMap);
		hintPure.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
		try{
			resultQR = readQRCode(filePath, charset, hintMap, hintPure);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Data read from QR Code: "+ resultQR);
		return resultQR;
	}
	
	public static String readQRCode(String filePath, String charset, Map<DecodeHintType,Object> hintMap, Map<DecodeHintType,Object> hintPure)
			throws FileNotFoundException, IOException, NotFoundException {
		BufferedImage image = ImageIO.read(new FileInputStream(filePath));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
		Reader reader = new MultiFormatReader();
		ReaderException savedException = null;
		try {
			// Look for pure barcode
			Result theResult = reader.decode(bitmap, hintPure);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			// Look for normal barcode in photo
			Result theResult = reader.decode(bitmap, hintMap);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			// Try again with other binarizer
			BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result theResult = reader.decode(hybridBitmap, hintMap);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
		} catch (FormatException | ChecksumException e) {
			System.out.println(e.toString());
		} catch (ReaderException e) { // Including NotFoundException
			System.out.println(e.toString());
		}

		return "";
	}

}
