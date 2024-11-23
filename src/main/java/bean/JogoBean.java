package bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import dao.CampeonatoDAO;
import dao.JogoDAO;
import entidade.Campeonato;
import entidade.Jogo;

@ManagedBean
@ViewScoped
public class JogoBean {
	private Integer campeonatoId;
	private Jogo jogo = new Jogo();
	private List<Campeonato> campeonatos;
	private List<Jogo> jogos;

	public List<Campeonato> getCampeonatos(){
		if(campeonatos == null) {
			campeonatos = CampeonatoDAO.Listar();
		}
		return campeonatos;
	}
	
	public List<Jogo> getJogos(){
		if (jogos == null) {
			jogos = JogoDAO.Listar();
		}
		return jogos;
	}
	
	public String salvar() {
		if(jogo.getTime1().equals(jogo.getTime2())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times de mesmo nome."));
            return null;
		}
		
        Campeonato campeonato = CampeonatoDAO.achar(campeonatoId);
        jogo.setCampeonato(campeonato);
	    
		jogo.setDataCadastro(new Date());
		
		JogoDAO.salvar(jogo);
		
		jogo = new Jogo();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo cadastrado com sucesso!"));
        
        return "listar_jogos?faces-redirect=true";
	}
	
    public void excluir(Jogo jogo) {
        JogoDAO.Excluir(jogo);
        jogos = JogoDAO.Listar();
    }

    public void editar(Jogo jogo) {
        JogoDAO.editar(jogo);
    }

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
    public Integer getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(Integer campeonatoId) {
        this.campeonatoId = campeonatoId;
    }
}
