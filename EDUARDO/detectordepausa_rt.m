% Classe que calcula a media dos dados quando uma nova pausa é detectada
classdef detectordepausa_rt < handle
   properties
       
       fator
       U
       Db
       Dsp
       Dsa
       
       %Filtro só pausa
       F
       FM
      
       %Filtro só ativo
       FF
       
   end
   methods
       %Construtor inializador do objeto  
        function obj = detectordepausa_rt
        
            obj.fator = 1.5;
            obj.U = 0.3;
            obj.Db = 0;
            obj.Dsp = 0;
            obj.Dsa = 0;
            
            [H,G] = cheby2(4,60,1/1000,'high');
            obj.F=FiltroIIR(H,G);
            
            HM=ones(1,50);
            HM=HM/sum(HM);
            obj.FM=FiltroIIR(HM,1);
            
            [H1,G1] = fir1(160,1/40,'low');
            obj.FF=FitroIIR(H1,G1);
            
        end
        
        %an: input signal  [1x3]
        %Db: detector de pausa [0 (não pausa) ou 1 (pausa)]
        %return :::
        %g0n: Nova média calculado enquanto esteve em pausa [1x3]
        %ready: Novo dado pronto [1] ou não [0] para ser utilizado
        
        function Db = detector_pausa(obj,an,nivel_ruido)
            
          obj.Dsp = obj.detector_so_pausa(an,nivel_ruido);
          
          obj.Dsa = obj.detector_so_ativo(an,nivel_ruido);
          
          if obj.Dsp == 1 && obj.Dsa == 0
          
              obj.Db = 1;
            
          else
              
              obj.Db = 0;
          
          end
          
          Db = obj.Db;
            
         end
        
        
         function Dsp = detector_so_pausa(obj,a_n2,ruido)
             
             a_n3 = obj.F.filter_rt(a_n2);
             a_n4 = abs(a_n3);
             a_n5 = obj.FM.filter_rt(a_n4);
             
             D = a_n5<=obj.fator*ruido;
             
             if sum(D)==3
                
                 obj.Dsp = 1;
                 
             else
                 
                 obj.Dsp = 0;
                 
             end
             
                Dsp = obj.Dsp; 
            
         end
          
         function Dsa = detector_so_ativo(obj,a_n2,ruido)
             
            
             a_x = a_n2 - obj.FF.filter_rt(a_n2);
             a_x2 = abs(a_x);
             
             D = a_x2>=obj.fator*ruido;
             
             Dx = D(1);
             Dy = D(2);
             Dz = D(3);
            
%              if (Dx(II)==0)
%                 
%                  Dx(II)=Dx(II-1)*exp(-Tau);
%              
%              end
% 
%             if (Dy(II)==0)
%         
%                 Dy(II)=Dy(II-1)*exp(-Tau);
%     
%             end
% 
%             if (Dz(II)==0)
%         
%                 Dz(II)=Dz(II-1)*exp(-Tau);
%     
%             end   
%           

            if ((Dx>obj.U) || (Dy>obj.U) || (Dz>obj.U))
         
                obj.Dsa = 1;
         
            else
         
                obj.Dsa = 0;
         
            end
            
                Dsa = obj.Dsa;
            
         end

   end
end