package pg.bioinf;

import pg.bioinf.prosite.InvalidPrositeExpressionException;
import pg.bioinf.prosite.PrositeExpression;
import pg.bioinf.prosite.PrositeExpression.ExpressionRange;

public class Main {
	public static void main(String[] args) throws InvalidPrositeExpressionException {
		System.out.println("Test 1");
		System.out.println("═══════════════════════════════════════════");
		PrositeExpression prexpr = PrositeExpression.parse("A-[AC]");
		System.out.println("A-[AC]");
		
		prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
		
		System.out.println("Test 2");
		System.out.println("═══════════════════════════════════════════");
		prexpr = PrositeExpression.parse("A-[AC](1,3)");
		System.out.println("A-[AC](1,4)");
		
		prexpr.printSubstring("ZAACDACKPPAAACDDDACKKAAAD");
		
		System.out.println("Test 2");
		System.out.println("═══════════════════════════════════════════");
		
		prexpr = PrositeExpression.parse("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
		System.out.println("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
		
		prexpr.printSubstring("SRSLKMRGQAFVIFKEVSSAT");
		prexpr.printSubstring("KLTGRPRGVAFVRYNKREEAQ");
		prexpr.printSubstring("VGCSVHKGFAFVQYVNERNAR");

	}
}
