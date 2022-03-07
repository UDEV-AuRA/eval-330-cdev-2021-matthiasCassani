package com.ipiecoles.java.eval.th330.controllers;

import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.repository.AlbumRepository;
import com.ipiecoles.java.eval.th330.repository.ArtistRepository;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;

@Controller
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @Autowired
    AlbumService albumService;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @GetMapping("/artistDetail/{id}")
    public String detail(@PathVariable Long id, final ModelMap model) {
        model.put("artistName", artistService.findById(id).getName());
        model.put("artistAlbums", artistService.findById(id).getAlbums());
        return "detailArtist";
    }

    @RequestMapping(value = "/artistDetail")
    public String detailName(final ModelMap model,
                             @RequestParam(value = "name", required = false) String name) {
        model.put("artistName", name);
        model.put("artistAlbums", artistRepository.findArtistByName(name).getAlbums());
        return "detailArtist";
    }

    @RequestMapping(value = "/artistList")
    public String detailName(final ModelMap model,
                             @RequestParam(value = "page", required = false) int page,
                             @RequestParam(value = "size", required = false) int size,
                             @RequestParam(value = "sortProperty", required = false) String sortProperty,
                             @RequestParam(value = "sortDirection", required = false) String sortDirection) {
        model.put("artistList", artistService.findAllArtists(page, size, sortProperty, sortDirection));
        return "listeArtists";
    }

    @RequestMapping(value="/save/{name}")
    public String save(@PathVariable String name, final ModelMap model,
                       @RequestParam(value = "newName", required = true) String newName) {
        artistRepository.findArtistByName(name).setName(newName);
        artistService.updateArtiste(artistRepository.findArtistByName(name).getId(), artistRepository.findArtistByName(name));
        model.put("artistName", artistRepository.findArtistByName(newName).getName());
        model.put("artistAlbums", artistRepository.findArtistByName(newName).getAlbums());
        return "detailArtist";
    }

    @RequestMapping(value="/delete/{name}")
    public String delete(@PathVariable String name) {
        System.out.println(name);
        artistService.deleteArtist(artistRepository.findArtistByName(name).getId());
        return "accueil";
    }
}
