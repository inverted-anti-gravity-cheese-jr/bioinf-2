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
	public static void main(String[] args) throws InvalidPrositeExpressionException, IOException {
		System.out.print("Czy uruchomic wbudowane testy? [T/n]");
		String line = System.console().readLine();
		
		if("T".equals(line) || "t".equals(line)) {
			System.out.println("Test 1");
			System.out.println("--------------------------------------------");
			PrositeExpression prexpr = PrositeExpression.parse("A-[AC]");
			System.out.println("Wyrazenie prosite");
			System.out.println("A-[AC]");
			System.out.println("Dopasowania");
			prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
			
			System.out.println("Test 2");
			System.out.println("--------------------------------------------");
			prexpr = PrositeExpression.parse("A-[AC](1,3)");
			System.out.println("Wyrazenie prosite");
			System.out.println("A-[AC](1,4)");
			System.out.println("Dopasowania");
			prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
			
			System.out.println("Test 3");
			System.out.println("--------------------------------------------");
			
			prexpr = PrositeExpression.parse("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
			System.out.println("Wyrazenie prosite");
			System.out.println("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
			System.out.println("Dopasowania");
			prexpr.printSubstring("SRSLKMRGQAFVIFKEVSSAT");
			prexpr.printSubstring("KLTGRPRGVAFVRYNKREEAQ");
			prexpr.printSubstring("VGCSVHKGFAFVQYVNERNAR");
			
			System.in.read();
		}
		else if("n".equals(line) || "N".equals(line)) {
			System.out.println("Prosze podac wyrazenie PROSITE");
			
			line = System.console().readLine();
			
			PrositeExpression prexpr = PrositeExpression.parse(line);
			
			while(true) {
				System.out.println("Prosze podac wyrazenie do testow (napisz /exit aby skonczyc)");
				
				line = System.console().readLine();
				
				if("/exit".equals(line)) {
					break;
				}
				
				prexpr.printSubstring(line);
			}
		}
		else {
			System.out.println("Blad");
			System.in.read();
		}
	}
}
