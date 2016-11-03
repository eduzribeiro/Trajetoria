% Classe que realiza a decisão da gravidade
classdef decide_g < handle
   properties
       
	a_n2
    g0n
    ready
    g_d
        
    DG
    MGP

   end
   methods
   % Construtor inializador do objeto
   % inival: inicia todas as variaveis
        function obj =decide_g

        obj.DG = decide(2); % Decide gravidade
        obj.MGP = media_g_pausa;

        obj.a_n2 = [0 0 0];
        obj.g0n = [0 0 0];
        obj.ready = 0;
        obj.g_d = [0 0 0];
        
        end
    %a_n: input signal  [1x3]
	%g0: input signal  [1x3]
    %Db: input signal  [0 ou 1]
    %return :::
	%a_n2: Aceleração subtraida a gravidade decidida [1x3]
        function a_n2 = decide_g_rt(obj,a_n,g0,Db)
            
         [obj.g0n , obj.ready] = obj.MGP.g_pausa_rt(a_n,Db);
         
         obj.g_d = obj.DG.decide_rt(g0,obj.g0n,obj.ready);
         
         obj.a_n2 = a_n - obj.g_d;
        
         a_n2 = obj.a_n2;
                
        end                   
        
   end
end