package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.CodigoFonte;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;

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
	
	public void inserirCodigoFonte(CodigoFonte cf)
	{
		Connection cff = ConnectionFactory.getConnection();
		java.sql.Date dataSql = new Date(cf.getDataColecao().getTime());
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM Projeto WHERE ";
			String insertSQL = "INSERT INTO `selfiecode`.`CODIGOFONTE` (`CODIGOFONTE`, `DATA_COLECAO`, `NOME_CLASSE`, `FK_US_PROJ`) VALUES (? ?,?,?)";


			PreparedStatement preparedStatement;
			try {
				preparedStatement = cff.prepareStatement(selectSQL);

				preparedStatement.setString(1, cf.getCodigoFonte());
				preparedStatement.setDate(2, dataSql);
				preparedStatement.setString(3, cf.getNomeClasse());
				preparedStatement.setString(4, cf.getNomeClasse());
				
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				int rs = preparedStatement.executeUpdate();
							

				cff.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	
}
