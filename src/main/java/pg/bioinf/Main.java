package pg.bioinf;

import pg.bioinf.prosite.PrositeExpression;
import pg.bioinf.prosite.PrositeExpression.ExpressionRange;

public class Main {
	public static void main(String[] args) {
		PrositeExpression prexpr = PrositeExpression.parse("[RK]-G-{EDRKHPCG}-[AGSCI]-[FY]-[LIVA]-x-[FYM]");
		ExpressionRange test1Sub = prexpr.findSubstring("SRSLKMRGQAFVIFKEVSSAT");
		ExpressionRange test2Sub = prexpr.findSubstring("KLTGRPRGVAFVRYNKREEAQ");
		ExpressionRange test3Sub = prexpr.findSubstring("VGCSVHKGFAFVQYVNERNAR");
		
		System.out.println(test1Sub);
		System.out.println(test2Sub);
		System.out.println(test3Sub);
	}
}
