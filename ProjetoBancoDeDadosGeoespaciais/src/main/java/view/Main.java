package view;

import java.util.List;
import java.util.Scanner;
import controller.EstoqueController;
import controller.FilialController;
import controller.PedidoController;
import controller.ProgramaController;
import controller.TransferenciaController;
import dto.EstoqueDTO;
import dto.FilialDTO;
import dto.ItemPedidoDTO;
import dto.LocalizacaoDTO;
import dto.PedidoDTO;
import dto.TransferenciaDTO;
import exception.EstoqueInsuficienteException;
import model.BuscaCoordenadas;

public class Main {
	public static void main(String[] args) {

	    Scanner scanner = new Scanner(System.in);
	    PedidoController controllerPedido = new PedidoController();
	    EstoqueController estoqueController = new EstoqueController();
	    TransferenciaController transferenciaController = new TransferenciaController();

	    int opcao = 0;
	    while (opcao != 9) {
	        System.out.println("\nMenu");
	        System.out.println("1. Cadastrar Filial");
	        System.out.println("2. Listar pedidos não concluídos");
	        System.out.println("3. Ver estoques");
	        System.out.println("4. Verificar estoques para pedido");
	        System.out.println("5. Listar transferências por filial");
	        System.out.println("6. Cancelar transferência");
	        System.out.println("7. Registrar chegada de transferência");
	        System.out.println("8. Excluir transferência");
	        System.out.println("9. Sair");
	        System.out.print("Escolha uma opção: ");
	        opcao = Integer.parseInt(scanner.nextLine());

	        switch (opcao) {
	            case 1:
	                System.out.println("Cadastro de Filial");
	                System.out.print("Nome: ");
	                String nome = scanner.nextLine();

	                System.out.print("Cidade: ");
	                String cidade = scanner.nextLine();

	                LocalizacaoDTO endereco = new LocalizacaoDTO();
	                endereco.setCidade(cidade);

	                FilialDTO filial = new FilialDTO();
	                FilialController controller = new FilialController();
	                filial.setNome(nome);

	                BuscaCoordenadas buscaCoordenadas = new BuscaCoordenadas();
	                try {
	                    filial.setEndereco(buscaCoordenadas.buscaLocalizacaoViaAPI(endereco));
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                controller.cadastrarFilial(filial);
	                break;

	            case 2:
	                try {
	                    for (PedidoDTO pedido : controllerPedido.listarPedidosPendentes()) {
	                        System.out.println("ID " + pedido.getId());
	                        System.out.println("Filial responsável: " + pedido.getFilialResponsavel().getNome());
	                        System.out.print("Itens: ");
	                        for (ItemPedidoDTO item : pedido.getItens()) {
	                            System.out.print(item.getQuantidade() + " " + item.getProduto().getNome() + " ");
	                        }
	                        System.out.println(" ");
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    opcao = 0;
	                    break;
	                }
	                break;

	            case 3:
	                try {
	                    for (EstoqueDTO estoque : estoqueController.listarEstoques()) {
	                        System.out.println("ID da Filial: "+estoque.getFilial().getId()+" "+estoque.getFilial().getNome() + " " +
	                                estoque.getProduto().getNome() + " " +
	                                estoque.getQuantidade());
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                break;

	            case 4:
	                System.out.print("Informe o ID do pedido para verificar a disponibilidade de estoques ou digite 0 para voltar: ");
	                int idPedido = Integer.parseInt(scanner.nextLine());

	                if (idPedido == 0) {
	                    break;
	                } else {
	                    PedidoDTO pedido = new PedidoDTO();
	                    pedido.setId(idPedido);
	                    
	                        try {
								pedido = controllerPedido.buscarPedidoPorId(pedido);
							} catch (Exception e) {
								System.out.println("Erro ao carregar os dados");
								e.printStackTrace();
								break;
							}
	                        if (pedido == null) {
	                            System.out.println("Pedido não encontrado");
	                            break;
	                        }

	                        FilialDTO filialResponsavel = pedido.getFilialResponsavel();
	                        List<TransferenciaDTO> transferencias;
							try {
								transferencias = estoqueController.necessitaTransferenciaDeEstoque(filialResponsavel, pedido);
								if (transferencias.isEmpty()) {
									System.out.print("Estoques disponíveis na filial. Atender pedido? [S/N]:");
									String resposta = scanner.nextLine();
									if (resposta.equalsIgnoreCase("S")) {
										try {
											estoqueController.atenderPedido(pedido);
											controllerPedido.concluirPedido(pedido);
											System.out.println("Pedido concluído com sucesso!");

										} catch (Exception e) {
											System.out.println("Erro ao carregar os dados");
											e.printStackTrace();
											break;
										}
										break;
									} else {
										break;
									}
								} else {
									System.out.println("Solicitar transferências de estoque? [S/N]");
									String resposta = scanner.nextLine();
									if (resposta.equalsIgnoreCase("S")) {
										for (TransferenciaDTO transferencia : transferencias) {
											System.out.println(transferencia.getQuantidade() + " " + transferencia.getProduto().getNome() + " vindo de " + transferencia.getOrigem().getNome());
											transferenciaController.registrarTransferencia(transferencia);
											controllerPedido.aguardarTransferencia(pedido);
											System.out.println("Transferências solicitadas com sucesso!");

										}
									
									} else {
										break;
									}
								}
							} catch (Exception e) {
								System.out.println("Erro ao carregar os dados");
								e.printStackTrace();
								break;
							}

	                    
	                }
	                break;

	            case 5:
	                System.out.print("Informe o ID da filial: ");
	                int idFilial = Integer.parseInt(scanner.nextLine());

	                FilialDTO filialConsulta = new FilialDTO();
	                filialConsulta.setId(idFilial);

	                try {
	                    List<TransferenciaDTO> transferencias = transferenciaController.listarTransferenciasPorFilial(filialConsulta);
	                    if (transferencias.isEmpty()) {
	                        System.out.println("Nenhuma transferência encontrada para esta filial.");
	                    } else {
	                        for (TransferenciaDTO transferencia : transferencias) {
	                            System.out.println("ID: " + transferencia.getId() +
	                                    ", Produto: " + transferencia.getProduto().getNome() +
	                                    ", Origem: " + transferencia.getOrigem().getNome() +
	                                    ", Destino: " + transferencia.getDestino().getNome() +
	                                    ", Quantidade: " + transferencia.getQuantidade());
	                        }
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                break;

	            case 6:
	                System.out.print("Informe o ID da transferência a ser cancelada: ");
	                int idTransferencia = Integer.parseInt(scanner.nextLine());

	                TransferenciaDTO transferenciaCancelar = new TransferenciaDTO();
	                transferenciaCancelar.setId(idTransferencia);

	                try {
	                    transferenciaController.cancelarTransferencia(transferenciaCancelar);
	                    System.out.println("Transferência cancelada com sucesso.");
	                } catch (Exception e) {
	                    System.out.println("Erro ao cancelar a transferência: " + e.getMessage());
	                    e.printStackTrace();
	                }
	                break;

	            case 7:
	                System.out.print("Informe o ID da transferência que chegou ao destino: ");
	                int idTransferenciaChegada = Integer.parseInt(scanner.nextLine());

	                TransferenciaDTO transferenciaChegada = new TransferenciaDTO();
	                transferenciaChegada.setId(idTransferenciaChegada);

	                try {
	                	transferenciaChegada = transferenciaController.buscarTransferenciaPorId(transferenciaChegada);
	                	estoqueController.transferir(transferenciaChegada);
	                    transferenciaController.registrarChegadaEstoque(transferenciaChegada);
	                    System.out.println("Transferência registrada como concluída.");
	                } catch (Exception e) {
	                    System.out.println("Erro ao registrar a chegada da transferência: " + e.getMessage());
	                    e.printStackTrace();
	                }
	                break;

	            case 8:
	            	System.out.print("Informe o ID da transferência a ser excluída: ");
	            	int idTransferenciaExcluir = Integer.parseInt(scanner.nextLine());

	            	TransferenciaDTO transferenciaExcluir = new TransferenciaDTO();
	            	transferenciaExcluir.setId(idTransferenciaExcluir);

	            	try {
	            		transferenciaExcluir = transferenciaController.buscarTransferenciaPorId(transferenciaExcluir);
	            		transferenciaController.excluirTransferencia(transferenciaExcluir);
	            		System.out.println("Transferência excluída com sucesso.");
	            	} catch (Exception e) {
	            		System.out.println("Erro ao excluir a transferência: " + e.getMessage());
	            		e.printStackTrace();
	            	}
	            	break;

	            case 9:
	            	System.out.println("Encerrando o programa...");
	            	break;

	            default:
	            	System.out.println("Opção inválida. Tente novamente.");
	        }
	    }

	    ProgramaController pc = new ProgramaController();
	    scanner.close();
	    pc.encerrar();
	}
}