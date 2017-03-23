% Classe que realiza a decisão da gravidade
classdef detectordepausa_inicio < handle
   properties
    
    alfa
    Db2
       
	FM
    FM2   
       
   end
   methods
   % Construtor inializador do objeto
   % inival: inicia todas as variaveis
        function obj =detectordepausa_inicio(KFactor)

            obj.alfa = 1.5;
            obj.Db2 = 0;
            
            HMA=ones(1,20*KFactor);
            HMA=HMA/sum(HMA);
            obj.FM=FiltroIIR(HMA,1);
            
            HMA2=ones(1,20*KFactor);
            HMA2=HMA2/sum(HMA2);
            obj.FM2=FiltroIIR(HMA2,1);
        
        end
    %a_n: input signal  [1x3]
	%g0: input signal  [1x3]
    %Db: input signal  [0 ou 1]
    %return :::
	%a_n2: Aceleração subtraida a gravidade decidida [1x3]
        function Db2 = detector_inicio(obj,a,ruido0)
            
            P = obj.FM.filter_rt(a);
            aa = a - P';
            P2 = abs(aa);
            y = obj.FM2.filter_rt(P2);
              
            D = y<=obj.alfa*ruido0;
            
            if sum(D)==3
                
                 obj.Db2 = 1;
                 
             else
                 
                 obj.Db2 = 0;
                 
             end
         
         Db2 = obj.Db2;
                
        end                   
        
   end
end