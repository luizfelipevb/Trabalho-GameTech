package com.example.trabalhogametech.controller;

import com.example.trabalhogametech.model.Jogo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    // Lista que armazenará os jogos em memória
    private List<Jogo> jogos = new ArrayList<>();


    // ==========================================================
    // GET - LISTAR TODOS OS JOGOS OU FILTRAR
    // ==========================================================

    @GetMapping
    public ResponseEntity<List<Jogo>> listarEFiltrar(

            // Filtro por gênero
            @RequestParam(required = false) String genero,

            // Filtro por plataforma
            @RequestParam(required = false) String plataforma,

            // Filtro por status
            @RequestParam(required = false) String status,

            // Filtro por avaliação
            @RequestParam(required = false) Double avaliacao) {

        // ==========================================================
        // ORDENA A LISTA PELO ID
        // ==========================================================

        // Coloca os jogos em ordem crescente de ID
        jogos.sort(Comparator.comparingInt(Jogo::getId));


        // ==========================================================
        // CASO NENHUM FILTRO SEJA INFORMADO
        // ==========================================================

        if (genero == null &&
                plataforma == null &&
                status == null &&
                avaliacao == null) {

            // Retorna todos os jogos já ordenados pelo ID
            return ResponseEntity.ok(jogos);
        }


        // ==========================================================
        // APLICA OS FILTROS
        // ==========================================================

        // Lista que receberá os resultados
        List<Jogo> resultados = new ArrayList<>();


        // Percorre todos os jogos cadastrados
        for (Jogo jogo : jogos) {

            // Começa considerando que o jogo corresponde aos filtros
            boolean corresponde = true;


            // ==================================================
            // FILTRO POR GÊNERO
            // ==================================================

            if (genero != null &&
                    !jogo.getGenero().equalsIgnoreCase(genero)) {

                // O jogo não corresponde ao gênero
                corresponde = false;
            }


            // ==================================================
            // FILTRO POR PLATAFORMA
            // ==================================================

            if (plataforma != null &&
                    !jogo.getPlataforma().equalsIgnoreCase(plataforma)) {

                // O jogo não corresponde à plataforma
                corresponde = false;
            }


            // ==================================================
            // FILTRO POR STATUS
            // ==================================================

            if (status != null &&
                    !jogo.getStatus().equalsIgnoreCase(status)) {

                // O jogo não corresponde ao status
                corresponde = false;
            }


            // ==================================================
            // FILTRO POR AVALIAÇÃO
            // ==================================================

            if (avaliacao != null &&
                    jogo.getAvaliacao() != avaliacao) {

                // O jogo não corresponde à avaliação
                corresponde = false;
            }


            // ==================================================
            // ADICIONA O JOGO AOS RESULTADOS
            // ==================================================

            if (corresponde) {
                resultados.add(jogo);
            }
        }


        // ==========================================================
        // ORDENA OS RESULTADOS PELO ID
        // ==========================================================

        resultados.sort(Comparator.comparingInt(Jogo::getId));


        // Retorna os jogos que correspondem aos filtros
        // e estão ordenados pelo ID
        return ResponseEntity.ok(resultados);
    }


    // ==========================================================
    // GET - BUSCAR JOGO PELO ID
    // ==========================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {

        // Percorre todos os jogos cadastrados
        for (Jogo jogo : jogos) {

            // Verifica se o ID do jogo é igual ao ID informado
            if (jogo.getId() == id) {

                // Retorna o jogo encontrado
                return ResponseEntity.ok(jogo);
            }
        }

        // Caso o ID não seja encontrado
        return ResponseEntity
                .status(404)
                .body("Jogo com ID " + id + " não foi encontrado.");
    }


    // ==========================================================
    // POST - CADASTRAR JOGO
    // ==========================================================

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Jogo jogo) {

        // Guarda o jogo que possui o mesmo ID, caso exista
        Jogo jogoMesmoId = null;

        // Guarda o jogo que possui o mesmo nome, caso exista
        Jogo jogoMesmoNome = null;


        // ==========================================================
        // VERIFICA ID E NOME DUPLICADOS
        // ==========================================================

        // Percorre todos os jogos já cadastrados
        for (Jogo jogoExistente : jogos) {

            // Verifica se o ID informado já existe
            if (jogoExistente.getId() == jogo.getId()) {

                // Guarda o jogo que possui esse ID
                jogoMesmoId = jogoExistente;
            }


            // Verifica se o nome informado já existe
            if (jogoExistente.getNome().equalsIgnoreCase(jogo.getNome())) {

                // Guarda o jogo que possui esse nome
                jogoMesmoNome = jogoExistente;
            }
        }


        // ==========================================================
        // ID E NOME JÁ EXISTEM NO MESMO JOGO
        // ==========================================================

        if (jogoMesmoId != null && jogoMesmoNome != null
                && jogoMesmoId == jogoMesmoNome) {

            // Cria uma resposta em formato JSON
            Map<String, Object> resposta = new LinkedHashMap<>();

            // Informa que o ID e o nome já existem
            resposta.put(
                    "mensagem",
                    "Não foi possível cadastrar o jogo. O ID "
                            + jogo.getId()
                            + " e o nome '"
                            + jogo.getNome()
                            + "' já estão sendo utilizados."
            );

            // Mostra o jogo existente apenas uma vez
            resposta.put("jogoExistente", jogoMesmoId);

            // Retorna HTTP 409 - Conflito
            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // ID E NOME JÁ EXISTEM EM JOGOS DIFERENTES
        // ==========================================================

        if (jogoMesmoId != null && jogoMesmoNome != null) {

            // Cria uma resposta em formato JSON
            Map<String, Object> resposta = new LinkedHashMap<>();

            // Informa que o ID e o nome já existem
            resposta.put(
                    "mensagem",
                    "Não foi possível cadastrar o jogo. O ID "
                            + jogo.getId()
                            + " e o nome '"
                            + jogo.getNome()
                            + "' já estão sendo utilizados."
            );

            // Mostra o jogo que possui o ID
            resposta.put("jogoDoId", jogoMesmoId);

            // Mostra o jogo que possui o nome
            resposta.put("jogoDoNome", jogoMesmoNome);

            // Retorna HTTP 409 - Conflito
            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // SOMENTE O ID JÁ EXISTE
        // ==========================================================

        if (jogoMesmoId != null) {

            // Cria uma resposta em formato JSON
            Map<String, Object> resposta = new LinkedHashMap<>();

            // Informa que o ID já existe
            resposta.put(
                    "mensagem",
                    "Não foi possível cadastrar o jogo. O ID "
                            + jogo.getId()
                            + " já está sendo utilizado."
            );

            // Mostra todas as informações do jogo existente
            resposta.put("jogoExistente", jogoMesmoId);

            // Retorna HTTP 409 - Conflito
            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // SOMENTE O NOME JÁ EXISTE
        // ==========================================================

        if (jogoMesmoNome != null) {

            // Cria uma resposta em formato JSON
            Map<String, Object> resposta = new LinkedHashMap<>();

            // Informa que o nome já existe
            resposta.put(
                    "mensagem",
                    "Não foi possível cadastrar o jogo. O nome '"
                            + jogo.getNome()
                            + "' já está sendo utilizado."
            );

            // Mostra todas as informações do jogo existente
            resposta.put("jogoExistente", jogoMesmoNome);

            // Retorna HTTP 409 - Conflito
            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // CADASTRO DO JOGO
        // ==========================================================

        // Adiciona o jogo na lista
        jogos.add(jogo);

        // Retorna o jogo cadastrado
        return ResponseEntity.ok(jogo);
    }


    // ==========================================================
    // PUT - ATUALIZAR JOGO
    // ==========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable int id,
            @RequestBody Jogo jogoAtualizado) {

        // Guarda o jogo que será atualizado
        Jogo jogoAtual = null;

        // Guarda o jogo que possui o novo ID, caso exista
        Jogo jogoMesmoId = null;

        // Guarda o jogo que possui o novo nome, caso exista
        Jogo jogoMesmoNome = null;


        // ==========================================================
        // PROCURA O JOGO QUE SERÁ ATUALIZADO
        // ==========================================================

        // Percorre todos os jogos cadastrados
        for (Jogo jogo : jogos) {

            // Procura o jogo pelo ID informado na URL
            if (jogo.getId() == id) {

                // Guarda o jogo encontrado
                jogoAtual = jogo;
            }
        }


        // ==========================================================
        // VERIFICA SE O ID INFORMADO NA URL EXISTE
        // ==========================================================

        if (jogoAtual == null) {

            return ResponseEntity
                    .status(404)
                    .body("Não foi possível atualizar o jogo. O ID "
                            + id + " não foi encontrado.");
        }


        // ==========================================================
        // VERIFICA SE O NOVO ID OU NOME JÁ EXISTEM
        // ==========================================================

        // Percorre todos os jogos cadastrados
        for (Jogo jogoExistente : jogos) {

            // Não compara o jogo com ele mesmo
            if (jogoExistente == jogoAtual) {
                continue;
            }


            // Verifica se outro jogo já possui o novo ID
            if (jogoExistente.getId() == jogoAtualizado.getId()) {

                jogoMesmoId = jogoExistente;
            }


            // Verifica se outro jogo já possui o novo nome
            if (jogoExistente.getNome()
                    .equalsIgnoreCase(jogoAtualizado.getNome())) {

                jogoMesmoNome = jogoExistente;
            }
        }


        // ==========================================================
        // NOVO ID E NOVO NOME JÁ EXISTEM EM OUTROS JOGOS
        // ==========================================================

        if (jogoMesmoId != null && jogoMesmoNome != null) {

            Map<String, Object> resposta = new LinkedHashMap<>();

            resposta.put(
                    "mensagem",
                    "Não foi possível atualizar o jogo. O ID "
                            + jogoAtualizado.getId()
                            + " e o nome '"
                            + jogoAtualizado.getNome()
                            + "' já estão sendo utilizados por outros jogos."
            );

            resposta.put("jogoDoId", jogoMesmoId);
            resposta.put("jogoDoNome", jogoMesmoNome);

            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // SOMENTE O NOVO ID JÁ EXISTE
        // ==========================================================

        if (jogoMesmoId != null) {

            Map<String, Object> resposta = new LinkedHashMap<>();

            resposta.put(
                    "mensagem",
                    "Não foi possível atualizar o jogo. O ID "
                            + jogoAtualizado.getId()
                            + " já está sendo utilizado por outro jogo."
            );

            resposta.put("jogoExistente", jogoMesmoId);

            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // SOMENTE O NOVO NOME JÁ EXISTE
        // ==========================================================

        if (jogoMesmoNome != null) {

            Map<String, Object> resposta = new LinkedHashMap<>();

            resposta.put(
                    "mensagem",
                    "Não foi possível atualizar o jogo. O nome '"
                            + jogoAtualizado.getNome()
                            + "' já está sendo utilizado por outro jogo."
            );

            resposta.put("jogoExistente", jogoMesmoNome);

            return ResponseEntity
                    .status(409)
                    .body(resposta);
        }


        // ==========================================================
        // ATUALIZA O JOGO
        // ==========================================================

        // Procura a posição do jogo antigo
        int indice = jogos.indexOf(jogoAtual);

        // Substitui pelo jogo atualizado
        jogos.set(indice, jogoAtualizado);

        // Retorna o jogo atualizado
        return ResponseEntity.ok(jogoAtualizado);
    }


    // ==========================================================
    // DELETE - EXCLUIR JOGO
    // ==========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id) {

        // Percorre a lista procurando o jogo pelo ID
        for (int i = 0; i < jogos.size(); i++) {

            // Verifica se encontrou o jogo
            if (jogos.get(i).getId() == id) {

                // Guarda o jogo antes de removê-lo
                Jogo jogoExcluido = jogos.get(i);

                // Remove o jogo da lista
                jogos.remove(i);

                // Retorna uma mensagem informando o jogo excluído
                return ResponseEntity.ok(
                        "Jogo '" + jogoExcluido.getNome()
                                + "' (ID: " + jogoExcluido.getId()
                                + ", Gênero: " + jogoExcluido.getGenero()
                                + ", Plataforma: " + jogoExcluido.getPlataforma()
                                + ") foi deletado com sucesso."
                );
            }
        }

        // Caso o ID não seja encontrado
        return ResponseEntity
                .status(404)
                .body("Não foi possível deletar o jogo. O ID "
                        + id + " não foi encontrado.");
    }
}