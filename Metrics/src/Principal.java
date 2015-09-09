import com.pucpr.br.bsi2015.tcc.selfiecode.controller.LogController;

public class Principal {
	public static void main(String[] args) {
		LogController lc = LogController.getInstance();
		lc.gerarLog("teste");
	}
}
