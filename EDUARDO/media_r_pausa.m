% Classe que calcula o ruido no sinal do acelerômetro quando uma nova pausa é detectada
classdef media_r_pausa < handle
   properties
        ruido0n
        ready
        L
        A
        aux
        F
        JJ
   end
   methods
       %Construtor inializador do objeto  
        function obj = media_r_pausa
        
            obj.ruido0n = 0;
            obj.ready = 0;
            obj.L = 0;
            obj.A = [0;0;0];
            obj.aux = 0;
            obj.JJ = 0;
            
            [H,G] = fir1(16,1/100,'low');          
            obj.F=FiltroIIR(H,G);
            
        end
        
        %an: input signal  [3x1]
        %Db: detector de pausa [0 (não pausa) ou 1 (pausa)]
        %return :::
        %ruido0n: Novo ruído calculado enquanto esteve em pausa [1x1]
        %ready: Novo dado pronto [1] ou não [0] para ser utilizado
        
         function [ruido0n , ready] = r_pausa_rt(obj,an,Db)
            
            if Db == 1
                              
                an = an - obj.F.filter_rt(an);
                
                if obj.JJ < 32
                   
                    an = [0;0;0];
                      
                end
                
                obj.JJ = obj.JJ + 1;
                
                an=an.^2;
                
                obj.A = obj.A + an;                
                obj.L = obj.L + 1;
                obj.aux = 1;
                obj.ready = 0;
                
            end
            
             if Db == 0 && obj.aux == 0
                
                obj.ready =0;
                
            end
            
            if Db == 0 && obj.aux == 1
               
                obj.ruido0n = sqrt(sum((obj.A)/obj.L));
                obj.ready = 1;
                obj.aux = 0;
                obj.L = 0;
                obj.A = 0;
                
            end
                   
            ruido0n = obj.ruido0n;
            ready = obj.ready;
            
          end

   end
end