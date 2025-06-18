package gcm.coinmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaPoupanca;
import gcm.coinmaster.model.ContaBonus;
import gcm.coinmaster.model.DTO.ContaDTO;
import gcm.coinmaster.model.DTO.TransferenciaDTO;
import gcm.coinmaster.service.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Conta> cadastrarConta(@RequestParam String numero, @RequestParam Double saldo) {
        Conta conta = contaService.cadastrarConta(numero, saldo);
        return ResponseEntity.ok(conta);
    }

    @PostMapping("/cadastrarPoupanca")
    public ResponseEntity<ContaPoupanca> cadastrarContaPoupanca(@RequestParam String numero, @RequestParam(required = false) Double saldoInicial) {
        ContaPoupanca contaPoupanca = contaService.cadastrarContaPoupanca(numero, saldoInicial);
        return ResponseEntity.ok(contaPoupanca);
    }

    @PostMapping("/renderJuros")
    public ResponseEntity<ContaPoupanca> renderJuros(@RequestParam String numero, @RequestParam double taxa) { //taxa = 10 quer dizer 10%
        ContaPoupanca contaPoupanca;
        contaPoupanca = contaService.renderJuros(numero, taxa);
        return ResponseEntity.ok(contaPoupanca);
    }

    @PostMapping("/renderJurosTodos")
    public ResponseEntity<List<ContaPoupanca>> renderJurosTodos(@RequestParam double taxa) {
        List<ContaPoupanca> contaPoupancas;
        contaPoupancas = contaService.renderJurosTodos(taxa);
        return ResponseEntity.ok(contaPoupancas);
    }
    @PostMapping("/cadastrar-conta-bonus")
    public ResponseEntity<Conta> cadastrarContaBonus(@RequestParam String numero) {
        ContaBonus conta = contaService.cadastrarContaBonus(numero);
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

    @PostMapping("/debito")
    public ResponseEntity<Conta> debito(@RequestParam String numero, @RequestParam double valor) {
        Conta conta = contaService.debito(numero, valor);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/consultar-conta")
    public ResponseEntity<ContaDTO> consultarConta(@RequestParam String numero) {
        ContaDTO conta = contaService.consultarConta(numero);
        return ResponseEntity.ok(conta);
    }
}