package gcm.coinmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.DTO.TransferenciaDTO;
import gcm.coinmaster.service.ContaService;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Conta> cadastrarConta(@RequestParam String numero) {
        Conta conta = contaService.cadastrarConta(numero);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/cadastrar-conta")
    public String cadastrarContaInterface() {
        return "cadastrar-conta";
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransferenciaDTO> fazerTransferencia(@RequestParam String origem, @RequestParam String destino, @RequestParam double valor) {
        TransferenciaDTO transferenciaDTO = contaService.fazerTransferencia(origem, destino, valor);
        return ResponseEntity.ok(transferenciaDTO);
    }

    @GetMapping("/fazer-transferencia")
    public String fazerTransferenciaInterface() {
        return "fazer-transferencia";
    }
    
    @PostMapping("/credito")
    public ResponseEntity<Conta> porCredito(@RequestParam String numero, @RequestParam double credito) {
        Conta conta = contaService.porCredito(numero, credito);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/por-credito")
    public String porCreditoInterface() {
        return "por-credito";
    }

    @GetMapping("/consultar-saldo")
    public ResponseEntity<Double> consultarSaldo(@RequestParam String numero) {
        double saldo = contaService.consultarSaldo(numero);
        return ResponseEntity.ok(saldo);
    } 
}

