# SRE (Site Reliability Engineering)

## Acordo de Nível de Serviço (SLA, SLO, EB e SLI)

### SLA

> SLA (Service Level Agreement) é um acordo entre um provedor de serviços e um cliente que define os níveis de
> desempenho e disponibilidade esperados para um serviço. No contexto de APIs, um SLA pode definir, por exemplo, o tempo
> de resposta máximo esperado para uma solicitação ou a disponibilidade mínima esperada do serviço.

### SLO

> SLO (Service Level Objective) é um objetivo de desempenho ou disponibilidade para um serviço. Um SLO é normalmente
> definido com base em um SLA, mas pode ser mais rigoroso ou flexível. No contexto de APIs, um SLO pode definir, por
> exemplo, o tempo de resposta máximo esperado para 95% das solicitações ou a disponibilidade mínima esperada de 99,9%
> do
> tempo.

### EB

> EB (Error Budget) é um orçamento de erros que pode ser usado para calcular o risco de um SLO não ser atendido. O EB é
> calculado subtraindo o SLO do SLA. No contexto de APIs, um EB pode ser usado para calcular o risco de um SLA de tempo
> de
> resposta de 2 segundos não ser atendido, por exemplo, se o SLO for de 2 segundos e o EB for de 1 segundo.

### SLI

> SLI (Service Level Indicator) é um indicador de desempenho ou disponibilidade que é usado para medir o desempenho ou a
> disponibilidade de um serviço. No contexto de APIs, um SLI pode ser, por exemplo, o tempo de resposta médio ou o
> número
> de erros por minuto.

#### Exemplo 1

##### SLA, SLO, EB e SLI para API REST de venda de produtos

| Conceitos | Valor                                      |
|-----------|--------------------------------------------|
| SLA       | Tempo de resposta máximo: 2 segundos       |
| SLO       | Tempo de resposta máximo: 1 segundo        |
| EB        | 0,5 segundos                               |
| SLI       | Tempo de resposta médio: 500 milissegundos |
| SLI       | Número de erros por minuto: 0,1            |

**Explicação**

* O SLA define os níveis de desempenho e disponibilidade esperados para a API.
* O SLO define um objetivo de desempenho mais rigoroso que o SLA.
* O EB é um orçamento de erros que pode ser usado para calcular o risco de um SLO não ser atendido.
* Os SLIs são indicadores de desempenho que são usados para medir o desempenho da API.

#### Exemplo 2

| Métrica           | SLA                                                          | SLI                                                | SLO                                                                | Observações                                                                                                         |
|-------------------|--------------------------------------------------------------|----------------------------------------------------|--------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| Disponibilidade   | 99,9%                                                        | Porcentagem de tempo em que o site está disponível | 99,95%                                                             | O site deve estar disponível 99,9% do tempo, o que significa que ele estará fora do ar por no máximo 0,1% do tempo. |
| Desempenho        | Tempo de carregamento da página inicial menor que 3 segundos | Tempo médio de carregamento da página inicial      | Tempo médio de carregamento da página inicial menor que 2 segundos | A página inicial deve carregar em no máximo 3 segundos.                                                             |
| Tempo de resposta | Resposta a consultas em menos de 1 segundo                   | Tempo médio de resposta a consultas                | Tempo médio de resposta a consultas menor que 0,5 segundos         | As consultas devem ser respondidas em no máximo 1 segundo.                                                          |

## 4 Golden Signals

> Os quatro sinais dourados (four golden signals) são um conjunto de métricas que são usadas para monitorar a saúde de
> um sistema. Eles são:
> - Latência: O tempo que leva para um sistema responder a uma solicitação.
> - Tráfego: O número de solicitações que um sistema recebe.
> - Saturação: O uso de recursos por um sistema.
> - Erros: O número de solicitações que falham.

### Metodologia USE e RED

> Método USE
> - Utilização (Utilization): A porcentagem de recursos do sistema que estão sendo usados.
> - Saturação (Saturation): A porcentagem de solicitações que estão sendo rejeitadas ou demorando mais do que o esperado
    para serem atendidas.
> - Erros (Errors): O número de solicitações que falharam.

> Método RED
> - Taxa (Rate): O número de solicitações recebidas pelo sistema por unidade de tempo.
> - Erros (Errors): O número de solicitações que falharam.
> - Duração (Duration): O tempo médio que leva para o sistema atender a uma solicitação.

### Redução do Tempo Médio de Detecção/Reparo/Resposta (MTTD/MTTR/MTRS)

> MTTD (Mean Time To Detect) é o tempo médio que leva para detectar uma falha. É uma métrica importante para a
> segurança, pois indica o tempo que leva para um invasor ou outra ameaça ser detectado.

> MTTR (Mean Time To Repair) é o tempo médio que leva para reparar uma falha. É uma métrica importante para a
> disponibilidade, pois indica o tempo que leva para um sistema ou serviço voltar a funcionar após uma falha.

> MTRS (Mean Time To Response): O MTRS é o tempo médio que leva para uma organização responder a um incidente de
> segurança após ele ser detectado. Essa métrica é essencial para avaliar a eficácia das equipes de resposta a incidentes
> de segurança.

| Métrica | Descrição                                          | Fórmula                                                            |
|---------|----------------------------------------------------|--------------------------------------------------------------------|
| MTTD    | Tempo médio que leva para detectar uma falha       | MTTD = Tempo total de falha / Número de falhas                     |
| MTTR    | Tempo médio que leva para reparar uma falha        | MTTR = Tempo total de reparo / Número de falhas                    |
| MTRS    | Tempo médio que leva para resposta de um incidente | MTRS = Tempo total de resposta a incidentes / Número de incidentes |
|

**Exemplos**

> * MTTD: Considere um sistema que falha a cada 100 horas. O MTTD para esse sistema seria de 100 horas. Isso significa que, em média, leva 100 horas para detectar uma falha após a ocorrência.

> * MTTR: Se o sistema mencionado anteriormente leva 5 horas para ser reparado após uma falha, o MTTR seria de 5 horas.

> * MTRS: Suponha que uma organização tenha experimentado 10 incidentes de segurança em um determinado período de tempo. O tempo total gasto em resposta a esses incidentes foi de 100 horas. O cálculo do MTRS seria o seguinte:
>
> > MTRS = 100 horas / 10 incidentes
>
> > TRS = 10 horas por incidente
> 
> Portanto, o MTRS dessa organização seria de 10 horas por incidente, o que indica que, em média, leva 10 horas para a organização responder a cada incidente de segurança.



**Observações**

* O MTTD, o MTTR e o MTRS são métricas críticas para avaliar a segurança e a disponibilidade de sistemas e serviços.
* Reduzir esses tempos é fundamental para minimizar o impacto de incidentes de segurança e melhorar a disponibilidade de sistemas.
* Essas métricas, quando usadas em conjunto, fornecem uma visão completa do ciclo de detecção, reparo e resposta a incidentes de segurança em uma organização.

## RCA
> RCA significa "Root Cause Analysis" (Análise da Causa Raiz) e é uma técnica usada para identificar a causa fundamental de um problema ou incidente. Isso ajuda a entender por que um evento indesejado ocorreu em primeiro lugar, em vez de apenas tratar os sintomas. A RCA é uma parte fundamental do processo de aprendizado organizacional e melhoria contínua. 