package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
	private String filtroTime;
	private List<Jogo> jogosFiltrados;

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
	    if (!isTimeValido(jogo.getTime1()) || !isTimeValido(jogo.getTime2())) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Os times devem ser A, B ou C."));
	        return null;
	    }

	    if (jogo.getTime1().equals(jogo.getTime2())) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times de mesmo nome."));
	        return null;
	    }

	    Campeonato campeonato = CampeonatoDAO.achar(campeonatoId);
	    if (campeonato == null) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Campeonato inválido."));
	        return null;
	    }
	    jogo.setCampeonato(campeonato);
	    jogo.setDataCadastro(new Date());

	    JogoDAO.salvar(jogo);

	    jogo = new Jogo();

	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo cadastrado com sucesso!"));
	    return "listagem,0?faces-redirect=true";
	}

	private boolean isTimeValido(String time) {
	    return "A".equals(time) || "B".equals(time) || "C".equals(time);
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
    
    public static class Resumo {
        private String time;
        private int pontos;
        private int vitorias;
        private int derrotas;
        private int empates;
        private int golsMarcados;
        private int golsSofridos;
        private int saldoGols;

        // Getters e Setters
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getPontos() {
            return pontos;
        }

        public void setPontos(int pontos) {
            this.pontos = pontos;
        }

        public int getVitorias() {
            return vitorias;
        }

        public void setVitorias(int vitorias) {
            this.vitorias = vitorias;
        }

        public int getDerrotas() {
            return derrotas;
        }

        public void setDerrotas(int derrotas) {
            this.derrotas = derrotas;
        }

        public int getEmpates() {
            return empates;
        }

        public void setEmpates(int empates) {
            this.empates = empates;
        }

        public int getGolsMarcados() {
            return golsMarcados;
        }

        public void setGolsMarcados(int golsMarcados) {
            this.golsMarcados = golsMarcados;
        }

        public int getGolsSofridos() {
            return golsSofridos;
        }

        public void setGolsSofridos(int golsSofridos) {
            this.golsSofridos = golsSofridos;
        }

        public int getSaldoGols() {
            return saldoGols;
        }

        public void setSaldoGols(int saldoGols) {
            this.saldoGols = saldoGols;
        }
    }

    
    private List<Resumo> resumoTimes;

    public void calcularResumo() {
        Map<String, Resumo> resumoMap = new HashMap<>();
        
        for (Jogo jogo : getJogos()) {
            atualizarResumo(resumoMap, jogo.getTime1(), jogo.getGolsTime1(), jogo.getGolsTime2());
            atualizarResumo(resumoMap, jogo.getTime2(), jogo.getGolsTime2(), jogo.getGolsTime1());
        }
        
        resumoTimes = new ArrayList<>(resumoMap.values());
    }

    private void atualizarResumo(Map<String, Resumo> resumoMap, String time, int golsMarcados, int golsSofridos) {
        Resumo resumo = resumoMap.getOrDefault(time, new Resumo());
        resumo.setTime(time);
        resumo.setGolsMarcados(resumo.getGolsMarcados() + golsMarcados);
        resumo.setGolsSofridos(resumo.getGolsSofridos() + golsSofridos);
        resumo.setSaldoGols(resumo.getSaldoGols() + (golsMarcados - golsSofridos));

        if (golsMarcados > golsSofridos) {
            resumo.setVitorias(resumo.getVitorias() + 1);
            resumo.setPontos(resumo.getPontos() + 3);
        } else if (golsMarcados == golsSofridos) {
            resumo.setEmpates(resumo.getEmpates() + 1);
            resumo.setPontos(resumo.getPontos() + 1);
        } else {
            resumo.setDerrotas(resumo.getDerrotas() + 1);
        }

        resumoMap.put(time, resumo);
    }

    public List<Resumo> getResumoTimes() {
        return resumoTimes;
    }
    
    
    
    public void filtrarPorTime() {
    	jogosFiltrados = JogoDAO.buscarPorTime(filtroTime);
    }

    public String getFiltroTime() {
        return filtroTime;
    }

    public void setFiltroTime(String filtroTime) {
        this.filtroTime = filtroTime;
    }

    public List<Jogo> getJogosFiltrados() {
        return jogosFiltrados;
    }
}
