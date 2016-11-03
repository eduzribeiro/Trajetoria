% Classe que realiza a decisão do ruído
classdef decide_r < handle
   properties
       
	ruido_d
    ruido0n
    ready
    
        
    DR
    MRP

   end
   methods
   % Construtor inializador do objeto
   % inival: inicia todas as variaveis
        function obj =decide_r

        obj.DR = decide(1); % Decide ruido
        obj.MRP = media_r_pausa;

        obj.ruido_d = 0;
        obj.ruido0n = 0;
        obj.ready = 0;
                
        end
    %a_n: input signal  [1x3]
	%ruido0: input signal  [1x1]
    %Db: input signal  [0 ou 1]
    %return :::
	%ruido_d: Ruido decidido [1x1]
        function ruido_d = decide_r_rt(obj,a_n,ruido0,Db)
            
         [obj.ruido0n , obj.ready] = obj.MRP.r_pausa_rt(a_n',Db);
         
         obj.ruido_d = obj.DR.decide_rt(ruido0,obj.ruido0n,obj.ready);
         
         ruido_d = obj.ruido_d;
                
        end                   
        
   end
end