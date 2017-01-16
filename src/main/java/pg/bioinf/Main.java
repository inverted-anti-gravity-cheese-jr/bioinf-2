package pg.bioinf;

import java.io.IOException;
import java.util.Arrays;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;

import pg.bioinf.prosite.InvalidPrositeExpressionException;
import pg.bioinf.prosite.PrositeExpression;
import pg.bioinf.prosite.PrositeExpression.ExpressionRange;

public class Main {
	private static final String PROSZE_PODAC_WYRAZENIE_PROSITE = "Prosze podac wyrazenie PROSITE";
	private static final String PROSZE_PODAC_WYRAZENIE_DO_TESTOW = "Prosze podac wyrazenie do testow (napisz /exit aby skonczyc)";
	private static final String CZY_URUCHOMIC_WBUDOWANE_TESTY = "Czy uruchomic wbudowane testy? [T/n]";
	private static final String WYRAZENIE_PROSITE = "Wyrazenie PROSITE";
	private static final String DOPASOWANIA = "Znalezione dopasowania";

	public static void main(String[] args) throws InvalidPrositeExpressionException, IOException {
		System.out.print(CZY_URUCHOMIC_WBUDOWANE_TESTY);
		String line = System.console().readLine();
		
		if("T".equals(line) || "t".equals(line)) {
			System.out.println("Test 1");
			System.out.println("--------------------------------------------");
			PrositeExpression prexpr = PrositeExpression.parse("A-[AC]");
			System.out.println(WYRAZENIE_PROSITE);
			System.out.println("A-[AC]");
			System.out.println(DOPASOWANIA);
			prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
			
			System.out.println("Test 2");
			System.out.println("--------------------------------------------");
			prexpr = PrositeExpression.parse("A-[AC](1,3)");
			System.out.println(WYRAZENIE_PROSITE);
			System.out.println("A-[AC](1,4)");
			System.out.println(DOPASOWANIA);
			prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
			
			System.out.println("Test 3");
			System.out.println("--------------------------------------------");
			
			prexpr = PrositeExpression.parse("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
			System.out.println(WYRAZENIE_PROSITE);
			System.out.println("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
			System.out.println(DOPASOWANIA);
			prexpr.printSubstring("SRSLKMRGQAFVIFKEVSSAT");
			prexpr.printSubstring("KLTGRPRGVAFVRYNKREEAQ");
			prexpr.printSubstring("VGCSVHKGFAFVQYVNERNAR");
			
			System.in.read();
		}
		else if("n".equals(line) || "N".equals(line)) {
			System.out.println(PROSZE_PODAC_WYRAZENIE_PROSITE);
			
			line = System.console().readLine();
			
			PrositeExpression prexpr = PrositeExpression.parse(line);
			
			while(true) {
				System.out.println(PROSZE_PODAC_WYRAZENIE_DO_TESTOW);
				
				line = System.console().readLine();
				
				if("/exit".equals(line)) {
					break;
				}
				
				System.out.println(DOPASOWANIA);
				prexpr.printSubstring(line);
			}
		}
		else {
			System.out.println("Blad");
			System.in.read();
		}
	}
}
