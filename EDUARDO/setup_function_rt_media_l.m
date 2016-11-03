% Classe que tira a media dos L primeiros dados
classdef setup_function_rt_media_l < handle
   properties
        g0
        I
        Si
        L
   end
   methods
        %Construtor inializador do objeto
        % inival: inicia todas as variaveis
        function obj =setup_function_rt_media_l(L)
        
            obj.g0=[0 0 0];
            obj.I=0;
            obj.Si=[0 0 0];
            obj.L=L;
            
        end
        %ar: input signal  [1x3]
        %return :::
        %g0: A media dos L primeiros [1x3]
        function g0 = media_de_l(obj,ar)
            
            if obj.I < obj.L+1
                
                obj.Si = obj.Si + ar;                
                obj.I = obj.I + 1;          
                
            end
            
            if obj.I == obj.L+1
               
                obj.g0 = (obj.Si)/obj.L;
                
            end
        
            g0 = obj.g0;
            
          end

   end
end