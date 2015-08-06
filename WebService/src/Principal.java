import com.pucpr.br.bsi2015.tcc.selfiecode.dao.ProjetoDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class Principal {
	public static void main(String[] args) {
		new ProjetoDAO().selectProjetos(new Usuario(7700147981l));
//		Connection cf = new ConnectionFactory().getConnection();
//		
//		if(cf == null)
//			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
//		else
//		{
//			String selectSQL = "SELECT * FROM USUARIO WHERE cpf = ?";
//			PreparedStatement preparedStatement;
//			try {
//				preparedStatement = cf.prepareStatement(selectSQL);
//				JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
//				preparedStatement.setLong(1, 7700147981l);
//				//preparedStatement.setString(1, "Pedro Henrique Banali");
//				ResultSet rs = preparedStatement.executeQuery();
//				while (rs.next()) {
//					String cpf = rs.getString("CPF");
//					String username = rs.getString("Nome");	
//					
//					JOptionPane.showConfirmDialog(null, "cpf: " + cpf + " username = " + username );
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//		}
			
	}
}
