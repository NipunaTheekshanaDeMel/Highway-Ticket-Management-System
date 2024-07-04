package lk.ijse.ticket_service.util;

import lk.ijse.ticket_service.dto.TicketDTO;
import lk.ijse.ticket_service.entity.TicketEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class Conversion {

    private final ModelMapper modelMapper;

    public TicketEntity toTicketEntity(TicketDTO ticketDTO){
        return modelMapper.map(ticketDTO, TicketEntity.class);
    }

    public TicketDTO toTicketDTO(TicketEntity ticketEntity){
        return modelMapper.map(ticketEntity, TicketDTO.class);
    }

    public List<TicketDTO> toTicketDTOList(List<TicketEntity> ticketEntities){
        return modelMapper.map(ticketEntities, List.class);
    }

}
