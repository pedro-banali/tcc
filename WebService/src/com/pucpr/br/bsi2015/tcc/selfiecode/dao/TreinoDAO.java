package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Treino;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class TreinoDAO {
public boolean inserirTreino(Treino treino) {
		
				
		Connection cf = ConnectionFactory.getConnection();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			
			String selectSQL = "INSERT INTO TREINO ( DESCRICAO, DURACAO, ASSUNTO, PROFESSOR  ) VALUES (?,?,?,?)";
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, treino.getDescricaoTreino());
				preparedStatement.setInt(2, treino.getDuracaoTreino());
				preparedStatement.setString(3, treino.getAssunto());
				preparedStatement.setString(4, treino.getProfessor());

				
				int rs = preparedStatement.executeUpdate();
				ResultSet rsKeys = preparedStatement.getGeneratedKeys();
				
				if (rsKeys.next()) {
					treino.setIdTreino(rsKeys.getInt(1));
				}
				if(rs > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}
public List<Treino> selectTreinos() {
	Treino treino;
	

	Connection cf = ConnectionFactory.getConnection();
	List treinos = new ArrayList<Treino>();
	
	if(cf == null)
		JOptionPane.showConfirmDialog(null, "ERRRROUUU");
	else
	{
		String selectSQL = "SELECT * FROM TREINO";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = cf.prepareStatement(selectSQL);
			//JOptionPane.showMessageDialog(null, BigInteger.valueOf(7700147981l));
			//preparedStatement.setLong(1, 7700147981l);

			//preparedStatement.setString(1, "Pedro Henrique Banali");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				treino = new Treino();
				treino.setIdTreino(rs.getInt("ID_TREINO"));
				treino.setDescricaoTreino(rs.getString("DESCRICAO"));
				treino.setDuracaoTreino(rs.getInt("DURACAO"));
				treino.setAssunto(rs.getString("ASSUNTO"));
								
				
				treinos.add(treino);
								
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return treinos;
}
}
