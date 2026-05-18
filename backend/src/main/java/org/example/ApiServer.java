package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonDeserializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import entidades.Aluno;
import entidades.Instrutor;
import entidades.Matricula;
import entidades.Pagamento;
import entidades.Plano;
import operacoes.AlunoOperacoes;
import operacoes.InstrutorOperacoes;
import operacoes.MatriculaOperacoes;
import operacoes.PagamentoOperacoes;
import operacoes.PlanoOperacoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ApiServer {

    private static final int PORT = 8080;
    private static final AlunoOperacoes alunoOps = new AlunoOperacoes();
    private static final InstrutorOperacoes instrutorOps = new InstrutorOperacoes();
    private static final PlanoOperacoes planoOps = new PlanoOperacoes();
    private static final MatriculaOperacoes matriculaOps = new MatriculaOperacoes();
    private static final PagamentoOperacoes pagamentoOps = new PagamentoOperacoes();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, t, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, t, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/api/alunos", new CrudHandler("alunos"));
            server.createContext("/api/instrutores", new CrudHandler("instrutores"));
            server.createContext("/api/planos", new CrudHandler("planos"));
            server.createContext("/api/matriculas", new CrudHandler("matriculas"));
            server.createContext("/api/pagamentos", new CrudHandler("pagamentos"));
            server.createContext("/", new StaticFileHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("=========================================");
            System.out.println(" Servidor Web: http://localhost:" + PORT);
            System.out.println("=========================================");
        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
        }
    }

    private static void sendJson(HttpExchange ex, int code, String json) throws IOException {
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static boolean handleCors(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) {
            ex.sendResponseHeaders(204, -1);
            return true;
        }
        return false;
    }

    private static int extractId(String path, String base) {
        String rest = path.replace(base, "").replace("/", "");
        if (rest.isEmpty())
            return -1;
        try {
            return Integer.parseInt(rest);
        } catch (Exception e) {
            return -1;
        }
    }

    static class CrudHandler implements HttpHandler {
        private final String entity;

        CrudHandler(String entity) {
            this.entity = entity;
        }

        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (handleCors(ex))
                return;
            String method = ex.getRequestMethod().toUpperCase();
            String path = ex.getRequestURI().getPath();
            int id = extractId(path, "/api/" + entity);

            try {
                switch (method) {
                    case "GET":
                        handleGet(ex);
                        break;
                    case "POST":
                        handlePost(ex);
                        break;
                    case "PUT":
                        handlePut(ex, id);
                        break;
                    case "DELETE":
                        handleDelete(ex, id);
                        break;
                    default:
                        ex.sendResponseHeaders(405, -1);
                }
            } catch (Exception e) {
                sendJson(ex, 500, "{\"erro\":\"" + e.getMessage() + "\"}");
            }
        }

        private void handleGet(HttpExchange ex) throws IOException {
            String json;
            switch (entity) {
                case "alunos":
                    json = gson.toJson(alunoOps.listarAlunos());
                    break;
                case "instrutores":
                    json = gson.toJson(instrutorOps.listarInstrutores());
                    break;
                case "planos":
                    json = gson.toJson(planoOps.listarPlanos());
                    break;
                case "matriculas":
                    json = gson.toJson(matriculaOps.listarMatriculas());
                    break;
                case "pagamentos":
                    json = gson.toJson(pagamentoOps.listarPagamentos());
                    break;
                default:
                    json = "[]";
            }
            sendJson(ex, 200, json);
        }

        private void handlePost(HttpExchange ex) throws IOException {
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            boolean ok = false;
            String erroValidacao = null;
            switch (entity) {
                case "alunos":
                    Aluno a = gson.fromJson(body, Aluno.class);
                    if (a.getDataNascimento() == null)
                        a.setDataNascimento(LocalDate.now());
                    if (a.getDataCadastro() == null)
                        a.setDataCadastro(LocalDate.now());
                    a.setAtivo(true);
                    erroValidacao = Validador.validarAluno(a);
                    if (erroValidacao == null) {
                        ok = alunoOps.cadastrarAluno(a);
                    }
                    break;
                case "instrutores":
                    Instrutor inst = gson.fromJson(body, Instrutor.class);
                    erroValidacao = Validador.validarInstrutor(inst);
                    if (erroValidacao == null) {
                        ok = instrutorOps.cadastrarInstrutor(inst);
                    }
                    break;
                case "planos":
                    Plano p = gson.fromJson(body, Plano.class);
                    erroValidacao = Validador.validarPlano(p);
                    if (erroValidacao == null) {
                        ok = planoOps.cadastrarPlano(p);
                    }
                    break;
                case "matriculas":
                    ok = matriculaOps.cadastrarMatricula(gson.fromJson(body, Matricula.class));
                    break;
                case "pagamentos":
                    Pagamento pg = gson.fromJson(body, Pagamento.class);
                    erroValidacao = Validador.validarPagamento(pg);
                    if (erroValidacao == null) {
                        ok = pagamentoOps.cadastrarPagamento(pg);
                    }
                    break;
            }
            if (erroValidacao != null) {
                sendJson(ex, 400, "{\"erro\":\"" + erroValidacao + "\"}");
            } else {
                sendJson(ex, ok ? 201 : 400, "{\"sucesso\":" + ok + "}");
            }
        }

        private void handlePut(HttpExchange ex, int id) throws IOException {
            if (id < 0) {
                sendJson(ex, 400, "{\"erro\":\"ID inválido\"}");
                return;
            }
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            boolean ok = false;
            String erroValidacao = null;
            switch (entity) {
                case "alunos":
                    Aluno a = gson.fromJson(body, Aluno.class);
                    a.setId(id);
                    erroValidacao = Validador.validarAluno(a);
                    if (erroValidacao == null) {
                        ok = alunoOps.atualizarAluno(a);
                        try {
                            com.google.gson.JsonObject jsonObj = com.google.gson.JsonParser.parseString(body)
                                    .getAsJsonObject();
                            if (jsonObj.has("idPlano") && !jsonObj.get("idPlano").isJsonNull()) {
                                int idPlano = jsonObj.get("idPlano").getAsInt();
                                if (idPlano > 0) {
                                    matriculaOps.atualizarPlanoDoAluno(id, idPlano);
                                } else {
                                    matriculaOps.deletarPorAluno(id);
                                }
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    break;
                case "instrutores":
                    Instrutor i = gson.fromJson(body, Instrutor.class);
                    Instrutor instrUpdate = new Instrutor(id, i.getNome(), i.getCpf(), i.getEmail(), i.getTelefone(),
                            i.getEspecialidade());
                    erroValidacao = Validador.validarInstrutor(instrUpdate);
                    if (erroValidacao == null) {
                        ok = instrutorOps.atualizarInstrutor(instrUpdate);
                    }
                    break;
                case "planos":
                    Plano p = gson.fromJson(body, Plano.class);
                    Plano planoUpdate = new Plano(id, p.getNome(), p.getValor());
                    erroValidacao = Validador.validarPlano(planoUpdate);
                    if (erroValidacao == null) {
                        ok = planoOps.atualizarPlano(planoUpdate);
                    }
                    break;
                case "pagamentos":
                    Pagamento pg = gson.fromJson(body, Pagamento.class);
                    Pagamento pagUpdate = new Pagamento(id, pg.getIdAluno(), pg.getValor(), pg.getStatus());
                    erroValidacao = Validador.validarPagamento(pagUpdate);
                    if (erroValidacao == null) {
                        ok = pagamentoOps.atualizarPagamento(pagUpdate);
                    }
                    break;
            }
            if (erroValidacao != null) {
                sendJson(ex, 400, "{\"erro\":\"" + erroValidacao + "\"}");
            } else {
                sendJson(ex, ok ? 200 : 400, "{\"sucesso\":" + ok + "}");
            }
        }

        private void handleDelete(HttpExchange ex, int id) throws IOException {
            if (id < 0) {
                sendJson(ex, 400, "{\"erro\":\"ID inválido\"}");
                return;
            }
            boolean ok = false;
            switch (entity) {
                case "alunos":
                    ok = alunoOps.deletarAluno(id);
                    break;
                case "instrutores":
                    ok = instrutorOps.deletarInstrutor(id);
                    break;
                case "planos":
                    ok = planoOps.deletarPlano(id);
                    break;
                case "pagamentos":
                    ok = pagamentoOps.deletarPagamento(id);
                    break;
            }
            sendJson(ex, ok ? 200 : 400, "{\"sucesso\":" + ok + "}");
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            String path = ex.getRequestURI().getPath();
            if (path.equals("/"))
                path = "/index.html";

            File file = new File("../frontend" + path);
            if (!file.exists())
                file = new File("frontend" + path);
            if (!file.exists())
                file = new File("../../frontend" + path);

            if (!file.exists() || file.isDirectory()) {
                String r = "404 - Frontend não encontrado.";
                ex.sendResponseHeaders(404, r.length());
                try (OutputStream os = ex.getResponseBody()) {
                    os.write(r.getBytes());
                }
                return;
            }
            if (path.endsWith(".css"))
                ex.getResponseHeaders().set("Content-Type", "text/css; charset=UTF-8");
            else if (path.endsWith(".js"))
                ex.getResponseHeaders().set("Content-Type", "application/javascript; charset=UTF-8");
            else if (path.endsWith(".html"))
                ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            ex.sendResponseHeaders(200, file.length());
            try (OutputStream os = ex.getResponseBody(); FileInputStream fis = new FileInputStream(file)) {
                fis.transferTo(os);
            }
        }
    }
}
