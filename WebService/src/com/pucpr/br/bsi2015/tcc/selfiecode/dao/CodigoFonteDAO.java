package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.CodigoFonte;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.session.SessionController;

public class CodigoFonteDAO {
	
//	SELECT * FROM CODIGO_METRICA CM, CODIGOFONTE CF, METRICA M 
//	WHERE CF.ID_CODIGO = CM.FK_ID_CODIGO
//	AND CM.FK_ID_METRICA = M.ID_METRICA
//	AND CF.FK_US_PROJ = 12
//	ORDER BY FK_ID_CODIGO;
	
	private List<CodigoFonte> codigoFonte;
	
	public List<CodigoFonte> selectCodigosFonte( Desenvolvedor dev, Projeto proj) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		List<CodigoFonte> codigosFontes = new ArrayList<CodigoFonte>();
		CodigoFonte cfo;
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT ID_CODIGO, DATA_COLECAO, NOME_CLASSE, FK_US_PROJ FROM CODIGOFONTE CF,"
					+ " USUARIO_PROJETO UP where CF.FK_US_PROJ = UP.ID "
					+ " AND UP.FK_USUARIO = ? "
					+ " AND UP.FK_PROJETO = ?";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				preparedStatement.setLong(1, dev.getCpf());
				preparedStatement.setInt(2, proj.getId());
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					cfo = new CodigoFonte();
					
					cfo.setDataColecao(rs.getDate("DATA_COLECAO"));
					cfo.setNomeClasse(rs.getString("NOME_CLASSE"));
					cfo.setId(rs.getInt("ID_CODIGO"));
					
					
					codigosFontes.add(cfo);
									
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return codigosFontes;
	}
	
	public int inserirCodigoFonte(CodigoFonte cf, int projId, String sessionId)
	{
		Connection cff = ConnectionFactory.getConnection();
		java.sql.Timestamp dataSql = new java.sql.Timestamp(cf.getDataColecao().getTime());
		
		//java.sql.Date dataSql = new Date(cf.getDataColecao().getTime());
		int ret = 0;
		int idUsuarioProj = 0;
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			SessionController sc = SessionController.getInstance();
			String selectSQL = "SELECT UP.ID "
					+ "FROM selfiecode.USUARIO INNER JOIN USUARIO_PROJETO UP ON  UP.FK_USUARIO = USUARIO.CPF "
					+ "INNER JOIN PROJETO ON UP.FK_PROJETO = PROJETO.ID_PROJETO "
					+ "WHERE USUARIO.CPF = ? "
					+ "AND PROJETO.ID_PROJETO = ?";
			Usuario u = sc.getUser(sessionId);
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cff.prepareStatement(selectSQL);

				preparedStatement.setLong(1, u.getCpf());
				preparedStatement.setInt(2, projId);
			
				
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					idUsuarioProj = rs.getInt("ID");								
				}
							

				//cff.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String insertSQL = "INSERT INTO `selfiecode`.`CODIGOFONTE` (`CODIGOFONTE`, `DATA_COLECAO`, `NOME_CLASSE`, `FK_US_PROJ`) VALUES (?,?,?,?)";


			//PreparedStatement preparedStatement;
			try {
				preparedStatement = cff.prepareStatement(insertSQL,  Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setString(1, cf.getCodigoFonte());
				preparedStatement.setTimestamp(2, dataSql);
				//preparedStatement.setDate(2, dataSql);
				preparedStatement.setString(3, cf.getNomeClasse());
				preparedStatement.setInt(4, idUsuarioProj);

				
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				int n  = preparedStatement.executeUpdate();
				int idMetrica = 0;
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()){
					ret = rs.getInt(1);
					
					for(Metrica m : cf.getMetricas())
					{
					
						selectSQL = "SELECT ID_METRICA FROM `selfiecode`.`METRICA` WHERE SIGLA = ?";
						//Usuario u = sc.getUser(sessionId);
						
						
						try {
							preparedStatement = cff.prepareStatement(selectSQL);

							preparedStatement.setString(1, m.getSigla());
							
						
							
							//preparedStatement.setString(1, "Pedro Henrique Banali");
							rs = preparedStatement.executeQuery();
							while (rs.next()) {
								idMetrica = rs.getInt("ID_METRICA");								
							}
										

							//cff.close();

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						insertSQL = "INSERT INTO `selfiecode`.`CODIGO_METRICA` (`FK_ID_METRICA`, `FK_ID_CODIGO`, `VALOR_METRICA`) VALUES (?,?,?);";
	
	
						//PreparedStatement preparedStatement;
						try {
							preparedStatement = cff.prepareStatement(insertSQL,  Statement.RETURN_GENERATED_KEYS);
	
							preparedStatement.setInt(1, idMetrica);
							preparedStatement.setInt(2, ret);
							preparedStatement.setFloat(3, m.getValorMetrica());

	
							
							//preparedStatement.setString(1, "Pedro Henrique Banali");
							preparedStatement.executeUpdate();
							
							rs = preparedStatement.getGeneratedKeys();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
					}
				}			
				//

				
				
				cff.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
	

	
}
