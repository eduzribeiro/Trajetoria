% Classe que calcula a media dos dados quando uma nova pausa é detectada
classdef media_g_pausa < handle
   properties
        g0n
        ready
        L
        A
        aux
   end
   methods
       %Construtor inializador do objeto  
        function obj = media_g_pausa
        
            obj.g0n = [0 0 0];
            obj.ready = 0;
            obj.L = 0;
            obj.A = [0 0 0];
            obj.aux =0;
            
        end
        
        %an: input signal  [1x3]
        %Db: detector de pausa [0 (não pausa) ou 1 (pausa)]
        %return :::
        %g0n: Nova média calculado enquanto esteve em pausa [1x3]
        %ready: Novo dado pronto [1] ou não [0] para ser utilizado
        
         function [g0n , ready] = g_pausa_rt(obj,an,Db)
            
            if Db == 1
                
                obj.A = obj.A + an;                
                obj.L = obj.L + 1;
                obj.aux = 1;
                obj.ready = 0;
                
            end
            
             if Db == 0 && obj.aux == 0
                
                obj.ready =0;
                
             end
            
             if Db == 0 && obj.aux == 1
                
                
                if obj.L>50
                    
                    obj.g0n = (obj.A)/obj.L;
                    obj.ready = 1;
                    obj.aux = 0;
                
                else
                    
                    obj.ready = 0;
                
                end
                
                    obj.L = 0;
                    obj.A = 0;
                
            end
                   
            g0n = obj.g0n;
            ready = obj.ready;
            
          end

   end
end