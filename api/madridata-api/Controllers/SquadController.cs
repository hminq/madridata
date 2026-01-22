using Microsoft.AspNetCore.Mvc;
using madridata_api.Services;
using madridata_api.Dtos.SquadPlayers;

namespace madridata_api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SquadController : ControllerBase
    {
        private readonly ISquadService _squadService;

        public SquadController(ISquadService squadService)
        {
            _squadService = squadService;
        }

        [HttpGet("players")]
        public async Task<ActionResult<List<SquadPlayerResponseDto>>> GetPlayers()
        {
            var players = await _squadService.GetTeamSquadAsync();
            return Ok(players);
        }
    }
}
