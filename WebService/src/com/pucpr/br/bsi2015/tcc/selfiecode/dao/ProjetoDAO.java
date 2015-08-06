package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class ProjetoDAO {
	
	private List<Projeto> projetos;
	
	public List<Projeto> selectProjetos(Usuario usuario) {
		Projeto projeto;
		

		Connection cf = ConnectionFactory.getConnection();
		projetos = new ArrayList<Projeto>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT * FROM USUARIO_PROJETO, PROJETO where FK_PROJETO = ID_PROJETO AND FK_USUARIO = ? LIMIT 0, 1000;";
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
				preparedStatement.setLong(1, 7700147981l);
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					projeto = new Projeto();
					projeto.setNome(rs.getString("NomeProjeto"));
					projeto.setDescricao(rs.getString("Descricao"));
					projeto.setDataInicio(rs.getDate("Inicio"));
					projeto.setDataFim(rs.getDate("Fim"));
					projeto.setStatus(rs.getString("Status_projeto"));
					
					System.out.println(projeto.getNome());
					
					
					projetos.add(projeto);
									
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return projetos;
	}
	
}
